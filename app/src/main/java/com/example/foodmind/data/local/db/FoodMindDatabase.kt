package com.example.foodmind.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodmind.data.local.dao.CategoryDao
import com.example.foodmind.data.local.dao.PriceQuoteDao
import com.example.foodmind.data.local.dao.ProductDao
import com.example.foodmind.data.local.dao.StoreDao
import com.example.foodmind.data.local.dao.StoreProductMappingDao
import com.example.foodmind.data.local.entity.CategoryEntity
import com.example.foodmind.data.local.entity.NutritionEntity
import com.example.foodmind.data.local.entity.PriceQuoteEntity
import com.example.foodmind.data.local.entity.ProductEntity
import com.example.foodmind.data.local.entity.StoreEntity
import com.example.foodmind.data.local.entity.StoreProductMappingEntity

@Database(
    entities = [
        CategoryEntity::class,
        ProductEntity::class,
        NutritionEntity::class,
        PriceQuoteEntity::class,
        StoreEntity::class,
        StoreProductMappingEntity::class
    ],
    version = 3,
    exportSchema = true
)
abstract class FoodMindDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
    abstract fun priceQuoteDao(): PriceQuoteDao
    abstract fun storeDao(): StoreDao
    abstract fun storeProductMappingDao(): StoreProductMappingDao
}
