package com.example.foodmind.di

import com.example.foodmind.dataimport.OpenFoodFactsImporter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImportModule {
    @Provides
    @Singleton
    fun provideOpenFoodFactsImporter(): OpenFoodFactsImporter {
        return OpenFoodFactsImporter()
    }
}
