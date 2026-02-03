package com.example.foodmind.data.repository

import com.example.foodmind.data.local.dao.StoreDao
import com.example.foodmind.data.local.dao.StoreProductMappingDao
import com.example.foodmind.data.mapper.toDomain
import com.example.foodmind.data.mapper.toEntity
import com.example.foodmind.domain.model.Store
import com.example.foodmind.domain.model.StoreProductMapping
import com.example.foodmind.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoreRepositoryImpl @Inject constructor(
    private val storeDao: StoreDao,
    private val storeProductMappingDao: StoreProductMappingDao
) : StoreRepository {
    override fun observeStores(): Flow<List<Store>> {
        return storeDao.observeStores()
            .map { stores -> stores.map { it.toDomain() } }
    }

    override suspend fun upsertStores(stores: List<Store>) {
        storeDao.upsertAll(stores.map { it.toEntity() })
    }

    override suspend fun upsertMappings(mappings: List<StoreProductMapping>) {
        storeProductMappingDao.upsertAll(mappings.map { it.toEntity() })
    }
}
