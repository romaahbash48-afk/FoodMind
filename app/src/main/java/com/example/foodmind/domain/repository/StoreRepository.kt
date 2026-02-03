package com.example.foodmind.domain.repository

import com.example.foodmind.domain.model.Store
import com.example.foodmind.domain.model.StoreProductMapping
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    fun observeStores(): Flow<List<Store>>
    suspend fun upsertStores(stores: List<Store>)
    suspend fun upsertMappings(mappings: List<StoreProductMapping>)
}
