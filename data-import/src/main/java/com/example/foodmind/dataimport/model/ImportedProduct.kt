package com.example.foodmind.dataimport.model

data class ImportedProduct(
    val name: String,
    val brand: String?,
    val barcode: String?,
    val imageUrl: String?,
    val countryTags: List<String>,
    val nutrition: ImportedNutrition?
)

data class ImportedNutrition(
    val kcal100: Double,
    val protein100: Double,
    val fat100: Double,
    val carbs100: Double,
    val sugar100: Double?,
    val salt100: Double?,
    val fiber100: Double?
)
