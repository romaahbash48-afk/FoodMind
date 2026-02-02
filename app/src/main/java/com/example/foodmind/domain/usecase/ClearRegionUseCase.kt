package com.example.foodmind.domain.usecase

import com.example.foodmind.di.IoDispatcher
import com.example.foodmind.domain.repository.RegionRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ClearRegionUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: RegionRepository
) : NoParamsUseCase<Unit>(dispatcher) {
    override suspend fun execute() {
        repository.clearRegion()
    }
}
