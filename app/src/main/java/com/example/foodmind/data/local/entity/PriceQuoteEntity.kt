package com.example.foodmind.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "price_quotes",
    primaryKeys = ["productId", "regionKey"]
)
data class PriceQuoteEntity(
    val productId: String,
    val regionKey: String,
    val currency: String,
    val avgPrice: Double,
    val source: String,
    val updatedAt: Long,
    val isMock: Boolean
)
