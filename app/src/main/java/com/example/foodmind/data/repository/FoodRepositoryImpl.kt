package com.example.foodmind.data.repository

import com.example.foodmind.data.mapper.toDomain
import com.example.foodmind.data.mapper.toDto
import com.example.foodmind.data.source.local.FoodLocalDataSource
import com.example.foodmind.data.source.remote.FoodRemoteDataSource
import com.example.foodmind.domain.model.FoodItem
import com.example.foodmind.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of FoodRepository.
 * Coordinates between local and remote data sources.
 * 
 * Follows the Single Source of Truth principle:
 * - Local database is the source of truth
 * - Remote API is used to sync data
 */
@Singleton
class FoodRepositoryImpl @Inject constructor(
    private val localDataSource: FoodLocalDataSource,
    private val remoteDataSource: FoodRemoteDataSource
) : FoodRepository {
    
    override fun getFoodItems(): Flow<List<FoodItem>> {
        // Return local data and observe changes
        return localDataSource.getFoodItems()
            .map { dtos -> dtos.toDomain() }
    }
    
    override suspend fun getFoodItemById(id: String): FoodItem? {
        return localDataSource.getFoodItemById(id)?.toDomain()
    }
    
    override suspend fun addFoodItem(item: FoodItem): Result<Unit> {
        return try {
            // Save locally
            localDataSource.saveFoodItem(item.toDto())
            
            // Sync with remote (optional, based on requirements)
            remoteDataSource.postFoodItem(item.toDto())
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun deleteFoodItem(id: String): Result<Unit> {
        return try {
            // Delete locally
            localDataSource.deleteFoodItem(id)
            
            // Sync with remote (optional)
            remoteDataSource.deleteFoodItem(id)
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
