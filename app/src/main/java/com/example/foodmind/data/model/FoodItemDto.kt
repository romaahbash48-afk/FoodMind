package com.example.foodmind.data.model

/**
 * Data Transfer Object for food items.
 * Used for data layer operations (API responses, database entities, etc.)
 */
data class FoodItemDto(
    val id: String,
    val name: String,
    val description: String,
    val calories: Int,
    val imageUrl: String? = null
)
