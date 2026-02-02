package com.example.foodmind.domain.usecase

import com.example.foodmind.di.IoDispatcher
import com.example.foodmind.domain.model.Product
import com.example.foodmind.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: ProductRepository
) : BaseUseCase<String, Product?>(dispatcher) {
    override suspend fun execute(params: String): Product? {
        return repository.getProduct(params)
    }
}
