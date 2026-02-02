package com.example.foodmind.domain.repository

import com.example.foodmind.domain.model.PriceQuote
import kotlinx.coroutines.flow.Flow

interface PriceRepository {
    fun observePriceQuote(productId: String, regionKey: String): Flow<PriceQuote?>
    suspend fun upsertPriceQuotes(quotes: List<PriceQuote>)
}
