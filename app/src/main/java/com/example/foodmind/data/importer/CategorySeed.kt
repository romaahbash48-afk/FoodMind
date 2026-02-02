package com.example.foodmind.data.importer

data class CategorySeed(
    val id: String,
    val name: String,
    val offTag: String,
    val parentId: String? = null
)
