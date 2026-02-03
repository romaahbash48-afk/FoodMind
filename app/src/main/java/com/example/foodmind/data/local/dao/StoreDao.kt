package com.example.foodmind.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodmind.data.local.entity.StoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {
    @Query("SELECT * FROM stores ORDER BY name")
    fun observeStores(): Flow<List<StoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(stores: List<StoreEntity>)

    @Query("SELECT * FROM stores WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): StoreEntity?
}
