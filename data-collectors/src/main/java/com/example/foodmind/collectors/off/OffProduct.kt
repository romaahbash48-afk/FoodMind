package com.example.foodmind.collectors.off

data class OffProduct(
    val name: String,
    val brand: String?,
    val barcode: String?,
    val imageUrl: String?,
    val countryTags: List<String>,
    val nutrition: OffNutrition?
)

data class OffNutrition(
    val kcal100: Double,
    val protein100: Double,
    val fat100: Double,
    val carbs100: Double,
    val sugar100: Double?,
    val salt100: Double?,
    val fiber100: Double?
)
