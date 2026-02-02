package com.example.foodmind.domain.usecase

import com.example.foodmind.domain.model.PriceQuote
import com.example.foodmind.domain.repository.PriceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePriceQuoteUseCase @Inject constructor(
    private val repository: PriceRepository
) {
    operator fun invoke(productId: String, regionKey: String): Flow<PriceQuote?> {
        return repository.observePriceQuote(productId, regionKey)
    }
}
