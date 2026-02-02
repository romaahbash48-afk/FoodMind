package com.example.foodmind.domain.usecase

import com.example.foodmind.di.IoDispatcher
import com.example.foodmind.domain.model.Region
import com.example.foodmind.domain.repository.RegionRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetRegionUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: RegionRepository
) : BaseUseCase<Region, Unit>(dispatcher) {
    override suspend fun execute(params: Region) {
        repository.setRegion(params)
    }
}
