package com.example.foodmind.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ProductWithNutrition(
    @Embedded val product: ProductEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    val nutrition: NutritionEntity?
)
