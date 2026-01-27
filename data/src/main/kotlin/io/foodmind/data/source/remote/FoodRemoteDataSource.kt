package io.foodmind.data.source.remote

import io.foodmind.domain.model.Food

/**
 * Interface for remote data source operations.
 * This would be implemented using Retrofit or other network client.
 */
interface FoodRemoteDataSource {
    /**
     * Fetch foods from remote API
     */
    suspend fun fetchFoods(): List<Food>
    
    /**
     * Fetch a specific food by ID from remote API
     */
    suspend fun fetchFoodById(id: String): Food?
    
    /**
     * Search foods on remote API
     */
    suspend fun searchFoods(query: String): List<Food>
}
