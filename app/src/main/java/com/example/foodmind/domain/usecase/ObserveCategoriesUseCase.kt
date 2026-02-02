package com.example.foodmind.domain.usecase

import com.example.foodmind.domain.model.Category
import com.example.foodmind.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCategoriesUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<Category>> {
        return repository.observeCategories()
    }
}
