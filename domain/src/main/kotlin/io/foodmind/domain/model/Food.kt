package io.foodmind.domain.model

/**
 * Domain model representing a Food item in the FoodMind application.
 * This is a pure Kotlin data class with no Android dependencies.
 */
data class Food(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val imageUrl: String? = null
)
