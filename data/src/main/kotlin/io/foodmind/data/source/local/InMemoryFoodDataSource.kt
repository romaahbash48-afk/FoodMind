package io.foodmind.data.source.local

import io.foodmind.domain.model.Food
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * In-memory implementation of FoodLocalDataSource.
 * This is a simple placeholder implementation for demonstration purposes.
 * In a real app, this would be replaced with Room database implementation.
 */
class InMemoryFoodDataSource : FoodLocalDataSource {
    
    // In-memory storage using a mutable list
    private val foods = mutableListOf<Food>()
    
    init {
        // Initialize with sample data
        foods.addAll(getSampleFoods())
    }
    
    override fun getAllFoods(): Flow<List<Food>> {
        return flowOf(foods.toList())
    }
    
    override suspend fun getFoodById(id: String): Food? {
        return foods.find { it.id == id }
    }
    
    override suspend fun saveFoods(foods: List<Food>) {
        this.foods.clear()
        this.foods.addAll(foods)
    }
    
    override suspend fun clearFoods() {
        foods.clear()
    }
    
    /**
     * Generate sample food data for demonstration
     */
    private fun getSampleFoods(): List<Food> = listOf(
        Food(
            id = "1",
            name = "Grilled Chicken Breast",
            description = "Lean protein source, perfect for muscle building",
            category = "Protein",
            calories = 165,
            protein = 31.0,
            carbs = 0.0,
            fat = 3.6
        ),
        Food(
            id = "2",
            name = "Brown Rice",
            description = "Whole grain with fiber and nutrients",
            category = "Carbs",
            calories = 216,
            protein = 5.0,
            carbs = 45.0,
            fat = 1.8
        ),
        Food(
            id = "3",
            name = "Avocado",
            description = "Healthy fats and vitamins",
            category = "Healthy Fats",
            calories = 160,
            protein = 2.0,
            carbs = 8.5,
            fat = 14.7
        ),
        Food(
            id = "4",
            name = "Greek Yogurt",
            description = "High protein dairy product",
            category = "Dairy",
            calories = 100,
            protein = 17.0,
            carbs = 6.0,
            fat = 0.7
        ),
        Food(
            id = "5",
            name = "Salmon Fillet",
            description = "Omega-3 rich fish",
            category = "Protein",
            calories = 206,
            protein = 22.0,
            carbs = 0.0,
            fat = 13.0
        )
    )
}
