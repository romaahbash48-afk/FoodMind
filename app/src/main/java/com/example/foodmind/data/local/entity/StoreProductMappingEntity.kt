package com.example.foodmind.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "store_product_mappings",
    primaryKeys = ["storeId", "retailerProductId"]
)
data class StoreProductMappingEntity(
    val storeId: String,
    val retailerProductId: String,
    val productId: String
)
