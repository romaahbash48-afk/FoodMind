package com.example.foodmind.data.source.remote

import com.example.foodmind.data.model.FoodItemDto
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Remote data source for food items.
 * In a real app, this would interact with Retrofit API client.
 * Currently returns mock data for demonstration.
 */
@Singleton
class FoodRemoteDataSource @Inject constructor() {
    
    /**
     * Fetches food items from remote API.
     */
    suspend fun getFoodItems(): List<FoodItemDto> {
        // In real app: make API call with Retrofit
        return emptyList()
    }
    
    /**
     * Fetches a single food item from remote API.
     */
    suspend fun getFoodItemById(id: String): FoodItemDto? {
        // In real app: make API call with Retrofit
        return null
    }
    
    /**
     * Posts a new food item to remote API.
     */
    suspend fun postFoodItem(item: FoodItemDto): Result<Unit> {
        // In real app: make API call with Retrofit
        return Result.success(Unit)
    }
    
    /**
     * Deletes a food item from remote API.
     */
    suspend fun deleteFoodItem(id: String): Result<Unit> {
        // In real app: make API call with Retrofit
        return Result.success(Unit)
    }
}
