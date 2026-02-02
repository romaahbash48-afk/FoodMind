package com.example.foodmind.data.source.local

import com.example.foodmind.data.model.FoodItemDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Local data source for food items.
 * In a real app, this would interact with Room database or DataStore.
 * Currently returns mock data for demonstration.
 */
@Singleton
class FoodLocalDataSource @Inject constructor() {
    
    // Large catalog of food items for demonstration
    private val mockFoodItems = FoodCatalogData.items
    
    /**
     * Gets all food items from local storage.
     */
    fun getFoodItems(): Flow<List<FoodItemDto>> {
        return flowOf(mockFoodItems)
    }
    
    /**
     * Gets a single food item by ID.
     */
    suspend fun getFoodItemById(id: String): FoodItemDto? {
        return mockFoodItems.find { it.id == id }
    }
    
    /**
     * Saves a food item to local storage.
     */
    suspend fun saveFoodItem(item: FoodItemDto) {
        // In real app: save to Room database
    }
    
    /**
     * Deletes a food item from local storage.
     */
    suspend fun deleteFoodItem(id: String) {
        // In real app: delete from Room database
    }
}
