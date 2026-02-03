package com.example.foodmind.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodmind.data.local.entity.StoreProductMappingEntity

@Dao
interface StoreProductMappingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(mappings: List<StoreProductMappingEntity>)

    @Query("SELECT * FROM store_product_mappings WHERE storeId = :storeId")
    suspend fun getByStore(storeId: String): List<StoreProductMappingEntity>
}
