package com.example.foodmind.data.pricing

import com.example.foodmind.data.remote.SupabaseSyncManager
import com.example.foodmind.domain.model.PriceQuote
import com.example.foodmind.domain.model.Product
import com.example.foodmind.domain.repository.PriceRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.absoluteValue

@Singleton
class MockPriceSeeder @Inject constructor(
    private val priceRepository: PriceRepository,
    private val supabaseSyncManager: SupabaseSyncManager
) {
    suspend fun seed(products: List<Product>, regionKey: String, currency: String) {
        if (products.isEmpty()) return
        val now = System.currentTimeMillis()
        val quotes = products.map { product ->
            val base = basePrice(product.category?.id)
            val cents = (product.id.hashCode().absoluteValue % 90) / 100.0
            PriceQuote(
                productId = product.id,
                regionKey = regionKey,
                currency = currency,
                avgPrice = base + cents,
                source = "mock-seed",
                updatedAt = now,
                isMock = true
            )
        }
        priceRepository.upsertPriceQuotes(quotes)
        supabaseSyncManager.upsertPriceQuotes(quotes)
    }

    private fun basePrice(categoryId: String?): Double {
        return when (categoryId) {
            "dairy" -> 2.2
            "cheese" -> 4.8
            "grains" -> 2.0
            "meat" -> 6.5
            "seafood" -> 7.2
            "vegetables" -> 2.4
            "fruits" -> 2.8
            "drinks" -> 1.9
            "snacks" -> 3.0
            "bakery" -> 2.5
            else -> 3.0
        }
    }
}
