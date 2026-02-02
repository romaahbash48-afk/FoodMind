package com.example.foodmind.domain.model

data class Category(
    val id: String,
    val name: String,
    val parentId: String? = null
)
