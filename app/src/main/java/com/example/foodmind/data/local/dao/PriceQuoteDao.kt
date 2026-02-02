package com.example.foodmind.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodmind.data.local.entity.PriceQuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceQuoteDao {
    @Query(
        """
        SELECT * FROM price_quotes
        WHERE productId = :productId AND regionKey = :regionKey
        LIMIT 1
        """
    )
    fun observePriceQuote(productId: String, regionKey: String): Flow<PriceQuoteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(quotes: List<PriceQuoteEntity>)
}
