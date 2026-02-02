package com.example.foodmind.domain.usecase

import com.example.foodmind.domain.model.Product
import com.example.foodmind.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(query: String, categoryId: String?): Flow<List<Product>> {
        return repository.observeProducts(query, categoryId)
    }
}
