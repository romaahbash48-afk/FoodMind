package com.example.foodmind.domain.model

data class Product(
    val id: String,
    val name: String,
    val brand: String?,
    val category: Category?,
    val barcode: String?,
    val imageUrl: String?,
    val countryTags: List<String>,
    val nutrition: Nutrition?,
    val createdAt: Long,
    val updatedAt: Long
)
