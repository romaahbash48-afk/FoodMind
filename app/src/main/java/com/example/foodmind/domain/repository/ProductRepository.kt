package com.example.foodmind.domain.repository

import com.example.foodmind.domain.model.Category
import com.example.foodmind.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun observeProducts(query: String, categoryId: String?): Flow<List<Product>>
    fun observeCategories(): Flow<List<Category>>
    suspend fun getProduct(id: String): Product?
    suspend fun upsertProducts(products: List<Product>)
    suspend fun upsertCategories(categories: List<Category>)
    suspend fun isCatalogEmpty(): Boolean
}
