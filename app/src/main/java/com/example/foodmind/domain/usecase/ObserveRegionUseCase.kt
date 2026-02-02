package com.example.foodmind.domain.usecase

import com.example.foodmind.domain.model.Region
import com.example.foodmind.domain.repository.RegionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveRegionUseCase @Inject constructor(
    private val repository: RegionRepository
) {
    operator fun invoke(): Flow<Region?> = repository.observeRegion()
}
