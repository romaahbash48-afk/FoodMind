package com.example.foodmind.di

import com.example.foodmind.collectors.off.OffCollector
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
    fun provideOffCollector(): OffCollector {
        return OffCollector()
    }
}
