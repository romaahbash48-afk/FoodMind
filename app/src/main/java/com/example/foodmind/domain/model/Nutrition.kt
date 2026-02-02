package com.example.foodmind.domain.model

data class Nutrition(
    val kcal100: Double,
    val protein100: Double,
    val fat100: Double,
    val carbs100: Double,
    val sugar100: Double?,
    val salt100: Double?,
    val fiber100: Double?
)
