package com.example.foodmind.di

import android.content.Context
import androidx.room.Room
import com.example.foodmind.data.local.dao.CategoryDao
import com.example.foodmind.data.local.dao.PriceQuoteDao
import com.example.foodmind.data.local.dao.ProductDao
import com.example.foodmind.data.local.db.FoodMindDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FoodMindDatabase {
        return Room.databaseBuilder(
            context,
            FoodMindDatabase::class.java,
            "foodmind.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCategoryDao(database: FoodMindDatabase): CategoryDao = database.categoryDao()

    @Provides
    fun provideProductDao(database: FoodMindDatabase): ProductDao = database.productDao()

    @Provides
    fun providePriceQuoteDao(database: FoodMindDatabase): PriceQuoteDao = database.priceQuoteDao()
}
