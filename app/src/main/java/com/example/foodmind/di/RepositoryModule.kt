package com.example.foodmind.di

import com.example.foodmind.data.repository.FoodRepositoryImpl
import com.example.foodmind.data.repository.PriceRepositoryImpl
import com.example.foodmind.data.repository.ProductRepositoryImpl
import com.example.foodmind.domain.repository.FoodRepository
import com.example.foodmind.domain.repository.PriceRepository
import com.example.foodmind.domain.repository.ProductRepository
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

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindPriceRepository(
        impl: PriceRepositoryImpl
    ): PriceRepository
}
