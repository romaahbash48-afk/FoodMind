package io.foodmind.data.source.local

import io.foodmind.domain.model.Food
import kotlinx.coroutines.flow.Flow

/**
 * Interface for local data source operations.
 * This could be implemented using Room, DataStore, or in-memory storage.
 */
interface FoodLocalDataSource {
    /**
     * Get all foods from local storage
     */
    fun getAllFoods(): Flow<List<Food>>
    
    /**
     * Get a food by ID from local storage
     */
    suspend fun getFoodById(id: String): Food?
    
    /**
     * Save foods to local storage
     */
    suspend fun saveFoods(foods: List<Food>)
    
    /**
     * Clear all foods from local storage
     */
    suspend fun clearFoods()
}
