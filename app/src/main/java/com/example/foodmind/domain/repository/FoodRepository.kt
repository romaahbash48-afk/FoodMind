package com.example.foodmind.domain.repository

import com.example.foodmind.domain.model.FoodItem
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for food items.
 * Defines the contract for data operations.
 * Implementation will be in the data layer.
 */
interface FoodRepository {
    /**
     * Gets a flow of food items.
     * Flow allows observing data changes.
     */
    fun getFoodItems(): Flow<List<FoodItem>>
    
    /**
     * Gets a single food item by ID.
     */
    suspend fun getFoodItemById(id: String): FoodItem?
    
    /**
     * Adds a new food item.
     */
    suspend fun addFoodItem(item: FoodItem): Result<Unit>
    
    /**
     * Deletes a food item by ID.
     */
    suspend fun deleteFoodItem(id: String): Result<Unit>
}
