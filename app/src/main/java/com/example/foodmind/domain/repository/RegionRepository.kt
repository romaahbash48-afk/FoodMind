package com.example.foodmind.domain.repository

import com.example.foodmind.domain.model.Region
import kotlinx.coroutines.flow.Flow

interface RegionRepository {
    fun observeRegion(): Flow<Region?>
    suspend fun setRegion(region: Region)
    suspend fun clearRegion()
}
