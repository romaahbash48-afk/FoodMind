package com.example.foodmind.domain.model

data class PriceQuote(
    val productId: String,
    val regionKey: String,
    val currency: String,
    val avgPrice: Double,
    val source: String,
    val updatedAt: Long,
    val isMock: Boolean
)
