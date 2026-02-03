package com.example.foodmind.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    indices = [
        Index(value = ["barcode"]),
        Index(value = ["name", "brand"])
    ]
)
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val brand: String?,
    val categoryId: String?,
    val barcode: String?,
    val imageUrl: String?,
    val countryTags: String?,
    val source: String,
    val createdAt: Long,
    val updatedAt: Long
)
