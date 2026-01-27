package io.foodmind.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.foodmind.data.repository.FoodRepositoryImpl
import io.foodmind.data.source.local.FoodLocalDataSource
import io.foodmind.data.source.local.InMemoryFoodDataSource
import io.foodmind.domain.repository.FoodRepository
import io.foodmind.domain.usecase.GetAllFoodsUseCase
import javax.inject.Singleton

/**
 * Hilt module for providing data layer dependencies.
 * Binds repository interfaces to their implementations.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    
    @Provides
    @Singleton
    fun provideFoodLocalDataSource(): FoodLocalDataSource {
        return InMemoryFoodDataSource()
    }
    
    @Provides
    @Singleton
    fun provideFoodRepository(
        localDataSource: FoodLocalDataSource
    ): FoodRepository {
        return FoodRepositoryImpl(localDataSource)
    }
    
    @Provides
    @Singleton
    fun provideGetAllFoodsUseCase(
        repository: FoodRepository
    ): GetAllFoodsUseCase {
        return GetAllFoodsUseCase(repository)
    }
}
