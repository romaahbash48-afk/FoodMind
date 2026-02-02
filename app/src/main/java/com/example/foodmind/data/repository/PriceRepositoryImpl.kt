package com.example.foodmind.data.repository

import com.example.foodmind.data.local.dao.PriceQuoteDao
import com.example.foodmind.data.mapper.toDomain
import com.example.foodmind.data.mapper.toEntity
import com.example.foodmind.domain.model.PriceQuote
import com.example.foodmind.domain.repository.PriceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PriceRepositoryImpl @Inject constructor(
    private val priceQuoteDao: PriceQuoteDao
) : PriceRepository {
    override fun observePriceQuote(productId: String, regionKey: String): Flow<PriceQuote?> {
        return priceQuoteDao.observePriceQuote(productId, regionKey)
            .map { entity -> entity?.toDomain() }
    }

    override suspend fun upsertPriceQuotes(quotes: List<PriceQuote>) {
        priceQuoteDao.upsertAll(quotes.map { it.toEntity() })
    }
}
