package com.example.foodmind.di

import com.example.foodmind.data.repository.FoodRepositoryImpl
import com.example.foodmind.domain.repository.FoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing repository implementations.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds the FoodRepository interface to its implementation.
     * Hilt will provide FoodRepositoryImpl when FoodRepository is requested.
     */
    @Binds
    @Singleton
    abstract fun bindFoodRepository(
        impl: FoodRepositoryImpl
    ): FoodRepository
}
