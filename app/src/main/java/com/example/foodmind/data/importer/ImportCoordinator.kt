package com.example.foodmind.data.importer

import com.example.foodmind.collectors.off.OffCollector
import com.example.foodmind.data.remote.SupabaseSyncManager
import com.example.foodmind.domain.model.Category
import com.example.foodmind.domain.model.Nutrition
import com.example.foodmind.domain.model.Product
import com.example.foodmind.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImportCoordinator @Inject constructor(
    private val offCollector: OffCollector,
    private val productRepository: ProductRepository,
    private val supabaseSyncManager: SupabaseSyncManager,
    @com.example.foodmind.di.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun seedCatalogIfEmpty(
        countryTag: String? = null
    ): ImportSummary {
        return withContext(ioDispatcher) {
            if (!productRepository.isCatalogEmpty()) {
                return@withContext ImportSummary(0, 0, skipped = 0)
            }

            val categories = ImportSeeds.categories.map { seed ->
                Category(id = seed.id, name = seed.name, parentId = seed.parentId)
            }
            val categoryMap = categories.associateBy { it.id }
            productRepository.upsertCategories(categories)
            supabaseSyncManager.upsertCategories(categories)

            val seenIds = mutableSetOf<String>()
            var importedCount = 0
            var skipped = 0
            val allImported = mutableListOf<Product>()

            ImportSeeds.categories.forEach { seed ->
                val products = offCollector.fetchCategory(
                    categoryTag = seed.offTag,
                    pages = DEFAULT_PAGES,
                    pageSize = DEFAULT_PAGE_SIZE,
                    countryTag = countryTag
                )
                val mapped = products.mapNotNull { imported ->
                    if (imported.imageUrl.isNullOrBlank()) {
                        skipped++
                        return@mapNotNull null
                    }
                    val nutrition = imported.nutrition ?: run {
                        skipped++
                        return@mapNotNull null
                    }
                    val id = makeProductId(imported.barcode, imported.name, imported.brand)
                    if (!seenIds.add(id)) {
                        skipped++
                        return@mapNotNull null
                    }
                    val product = Product(
                        id = id,
                        name = imported.name,
                        brand = imported.brand,
                        category = categoryMap[seed.id],
                        barcode = imported.barcode,
                        imageUrl = imported.imageUrl,
                        countryTags = imported.countryTags,
                        source = Product.Source.OPEN_FOOD_FACTS,
                        nutrition = Nutrition(
                            kcal100 = nutrition.kcal100,
                            protein100 = nutrition.protein100,
                            fat100 = nutrition.fat100,
                            carbs100 = nutrition.carbs100,
                            sugar100 = nutrition.sugar100,
                            salt100 = nutrition.salt100,
                            fiber100 = nutrition.fiber100
                        ),
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )
                    importedCount++
                    product
                }
                if (mapped.isNotEmpty()) {
                    productRepository.upsertProducts(mapped)
                    supabaseSyncManager.upsertProducts(mapped)
                    supabaseSyncManager.upsertNutrition(mapped)
                    allImported.addAll(mapped)
                }
            }

            ImportSummary(
                imported = importedCount,
                categories = categories.size,
                skipped = skipped
            )
        }
    }

    private fun makeProductId(barcode: String?, name: String, brand: String?): String {
        if (!barcode.isNullOrBlank()) return barcode
        val normalized = "${name.trim().lowercase()}|${brand?.trim()?.lowercase() ?: ""}"
        val digest = MessageDigest.getInstance("SHA-256")
            .digest(normalized.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }.take(32)
    }

    data class ImportSummary(
        val imported: Int,
        val categories: Int,
        val skipped: Int
    )

    private companion object {
        const val DEFAULT_PAGE_SIZE = 50
        const val DEFAULT_PAGES = 1
    }
}
