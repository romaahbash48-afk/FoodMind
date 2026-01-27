package com.example.foodmind.domain.model

/**
 * Example domain model representing a food item.
 * This is a pure domain model without Android dependencies.
 */
data class FoodItem(
    val id: String,
    val name: String,
    val description: String,
    val calories: Int,
    val imageUrl: String? = null
)
