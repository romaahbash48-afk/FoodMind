package com.example.foodmind.domain.usecase

import com.example.foodmind.di.IoDispatcher
import com.example.foodmind.domain.model.FoodItem
import com.example.foodmind.domain.repository.FoodRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving the full food catalog.
 */
class GetFoodItemsUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: FoodRepository
) : NoParamsUseCase<Flow<List<FoodItem>>>(dispatcher) {

    override suspend fun execute(): Flow<List<FoodItem>> {
        return repository.getFoodItems()
    }
}
