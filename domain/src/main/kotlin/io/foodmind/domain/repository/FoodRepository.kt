package io.foodmind.domain.repository

import io.foodmind.domain.model.Food
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for Food data operations.
 * Implementations are provided in the :data module.
 * This interface defines the contract for accessing food data.
 */
interface FoodRepository {
    /**
     * Get all available foods as a Flow for reactive updates
     */
    fun getAllFoods(): Flow<List<Food>>
    
    /**
     * Get a specific food by its ID
     */
    suspend fun getFoodById(id: String): Food?
    
    /**
     * Search foods by name or category
     */
    fun searchFoods(query: String): Flow<List<Food>>
}
