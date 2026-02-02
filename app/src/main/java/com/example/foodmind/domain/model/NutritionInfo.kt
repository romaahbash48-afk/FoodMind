package com.example.foodmind.domain.model

/**
 * Nutrition information for a standard serving.
 */
data class NutritionInfo(
    val calories: Int,
    val proteinGrams: Double,
    val fatGrams: Double,
    val carbsGrams: Double,
    val fiberGrams: Double,
    val sugarGrams: Double,
    val servingSizeGrams: Int
)
