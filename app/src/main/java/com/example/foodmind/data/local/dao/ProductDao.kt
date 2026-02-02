package com.example.foodmind.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.foodmind.data.local.entity.NutritionEntity
import com.example.foodmind.data.local.entity.ProductEntity
import com.example.foodmind.data.local.entity.ProductWithNutrition
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Transaction
    @Query(
        """
        SELECT * FROM products
        WHERE (:query == '' OR name LIKE '%' || :query || '%' OR brand LIKE '%' || :query || '%')
        AND (:categoryId IS NULL OR categoryId = :categoryId)
        ORDER BY name
        """
    )
    fun observeProducts(query: String, categoryId: String?): Flow<List<ProductWithNutrition>>

    @Transaction
    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    suspend fun getProduct(id: String): ProductWithNutrition?

    @Query("SELECT * FROM products WHERE barcode = :barcode LIMIT 1")
    suspend fun findByBarcode(barcode: String): ProductEntity?

    @Query("SELECT * FROM products WHERE name = :name AND brand = :brand LIMIT 1")
    suspend fun findByNameAndBrand(name: String, brand: String?): ProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProducts(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNutrition(nutrition: List<NutritionEntity>)

    @Query("SELECT COUNT(*) FROM products")
    suspend fun count(): Int
}
