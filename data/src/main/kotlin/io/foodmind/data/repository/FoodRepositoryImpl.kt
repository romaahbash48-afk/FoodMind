package io.foodmind.data.repository

import io.foodmind.data.source.local.FoodLocalDataSource
import io.foodmind.domain.model.Food
import io.foodmind.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of FoodRepository.
 * This class coordinates between local and remote data sources.
 * 
 * For now, it only uses the local data source. In a real application,
 * it would implement a caching strategy using both local and remote sources.
 */
class FoodRepositoryImpl(
    private val localDataSource: FoodLocalDataSource
) : FoodRepository {
    
    override fun getAllFoods(): Flow<List<Food>> {
        return localDataSource.getAllFoods()
    }
    
    override suspend fun getFoodById(id: String): Food? {
        return localDataSource.getFoodById(id)
    }
    
    override fun searchFoods(query: String): Flow<List<Food>> {
        return localDataSource.getAllFoods().map { foods ->
            foods.filter { food ->
                food.name.contains(query, ignoreCase = true) ||
                food.category.contains(query, ignoreCase = true) ||
                food.description.contains(query, ignoreCase = true)
            }
        }
    }
}
