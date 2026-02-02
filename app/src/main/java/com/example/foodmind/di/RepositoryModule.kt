package com.example.foodmind.di

import com.example.foodmind.data.repository.PriceRepositoryImpl
import com.example.foodmind.data.repository.ProductRepositoryImpl
import com.example.foodmind.data.repository.RegionRepositoryImpl
import com.example.foodmind.domain.repository.PriceRepository
import com.example.foodmind.domain.repository.ProductRepository
import com.example.foodmind.domain.repository.RegionRepository
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
     * Binds the ProductRepository interface to its implementation.
     * Hilt will provide ProductRepositoryImpl when ProductRepository is requested.
     */
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

    @Binds
    @Singleton
    abstract fun bindRegionRepository(
        impl: RegionRepositoryImpl
    ): RegionRepository
}
