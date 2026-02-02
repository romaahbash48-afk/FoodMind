package com.example.foodmind.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutrition")
data class NutritionEntity(
    @PrimaryKey val productId: String,
    val kcal100: Double,
    val protein100: Double,
    val fat100: Double,
    val carbs100: Double,
    val sugar100: Double?,
    val salt100: Double?,
    val fiber100: Double?
)
