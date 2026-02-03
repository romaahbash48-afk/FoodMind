package com.example.foodmind.data.mapper

import com.example.foodmind.data.local.entity.StoreEntity
import com.example.foodmind.data.local.entity.StoreProductMappingEntity
import com.example.foodmind.domain.model.Store
import com.example.foodmind.domain.model.StoreProductMapping

fun StoreEntity.toDomain(): Store {
    return Store(
        id = id,
        name = name,
        domain = domain
    )
}

fun Store.toEntity(): StoreEntity {
    return StoreEntity(
        id = id,
        name = name,
        domain = domain
    )
}

fun StoreProductMapping.toEntity(): StoreProductMappingEntity {
    return StoreProductMappingEntity(
        storeId = storeId,
        retailerProductId = retailerProductId,
        productId = productId
    )
}
