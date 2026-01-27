package io.foodmind.domain.usecase

import io.foodmind.domain.model.Food
import io.foodmind.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for retrieving all foods.
 * Encapsulates business logic for fetching food items.
 * 
 * Following Clean Architecture principles, use cases represent
 * the application's business rules and orchestrate data flow.
 */
class GetAllFoodsUseCase(
    private val foodRepository: FoodRepository
) {
    /**
     * Execute the use case to get all foods
     * @return Flow of list of Food items
     */
    operator fun invoke(): Flow<List<Food>> {
        return foodRepository.getAllFoods()
    }
}
