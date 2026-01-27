package com.example.foodmind.domain.usecase

import com.example.foodmind.di.IoDispatcher
import com.example.foodmind.domain.model.FoodItem
import com.example.foodmind.domain.repository.FoodRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Use case for retrieving a food item by its ID.
 * Encapsulates the business logic for fetching a single food item.
 */
class GetFoodItemByIdUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: FoodRepository
) : BaseUseCase<String, FoodItem?>(dispatcher) {
    
    override suspend fun execute(params: String): FoodItem? {
        return repository.getFoodItemById(params)
    }
}
