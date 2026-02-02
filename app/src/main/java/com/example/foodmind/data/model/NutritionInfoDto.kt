package com.example.foodmind.data.model

/**
 * DTO for nutrition information.
 */
data class NutritionInfoDto(
    val calories: Int,
    val proteinGrams: Double,
    val fatGrams: Double,
    val carbsGrams: Double,
    val fiberGrams: Double,
    val sugarGrams: Double,
    val servingSizeGrams: Int
)
