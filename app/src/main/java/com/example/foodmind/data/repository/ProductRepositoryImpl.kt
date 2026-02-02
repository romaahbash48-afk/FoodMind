package com.example.foodmind.data.repository

import com.example.foodmind.data.local.dao.CategoryDao
import com.example.foodmind.data.local.dao.ProductDao
import com.example.foodmind.data.mapper.toDomain
import com.example.foodmind.data.mapper.toEntity
import com.example.foodmind.domain.model.Category
import com.example.foodmind.domain.model.Product
import com.example.foodmind.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao
) : ProductRepository {
    override fun observeProducts(query: String, categoryId: String?): Flow<List<Product>> {
        return combine(
            productDao.observeProducts(query, categoryId),
            categoryDao.observeCategories()
        ) { products, categories ->
            val categoryMap = categories.associateBy { it.id }
            products.map { productWithNutrition ->
                val category = productWithNutrition.product.categoryId?.let { id ->
                    categoryMap[id]?.toDomain()
                }
                productWithNutrition.toDomain(category)
            }
        }
    }

    override fun observeCategories(): Flow<List<Category>> {
        return categoryDao.observeCategories()
            .map { categories -> categories.map { it.toDomain() } }
    }

    override suspend fun getProduct(id: String): Product? {
        val productWithNutrition = productDao.getProduct(id) ?: return null
        val category = productWithNutrition.product.categoryId?.let { categoryId ->
            categoryDao.getById(categoryId)?.toDomain()
        }
        return productWithNutrition.toDomain(category)
    }

    override suspend fun upsertProducts(products: List<Product>) {
        val productEntities = products.map { it.toEntity(it.category?.id) }
        val nutritionEntities = products.mapNotNull { product ->
            product.nutrition?.toEntity(product.id)
        }
        productDao.upsertProducts(productEntities)
        if (nutritionEntities.isNotEmpty()) {
            productDao.upsertNutrition(nutritionEntities)
        }
    }

    override suspend fun upsertCategories(categories: List<Category>) {
        categoryDao.upsertAll(categories.map { it.toEntity() })
    }

    override suspend fun isCatalogEmpty(): Boolean {
        return productDao.count() == 0
    }
}
