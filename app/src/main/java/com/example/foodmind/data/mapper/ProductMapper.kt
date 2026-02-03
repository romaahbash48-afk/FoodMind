package com.example.foodmind.data.mapper

import com.example.foodmind.data.local.entity.CategoryEntity
import com.example.foodmind.data.local.entity.NutritionEntity
import com.example.foodmind.data.local.entity.PriceQuoteEntity
import com.example.foodmind.data.local.entity.ProductEntity
import com.example.foodmind.data.local.entity.ProductWithNutrition
import com.example.foodmind.domain.model.Category
import com.example.foodmind.domain.model.Nutrition
import com.example.foodmind.domain.model.PriceQuote
import com.example.foodmind.domain.model.Product

fun CategoryEntity.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        parentId = parentId
    )
}

fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        parentId = parentId
    )
}

fun NutritionEntity.toDomain(): Nutrition {
    return Nutrition(
        kcal100 = kcal100,
        protein100 = protein100,
        fat100 = fat100,
        carbs100 = carbs100,
        sugar100 = sugar100,
        salt100 = salt100,
        fiber100 = fiber100
    )
}

fun Nutrition.toEntity(productId: String): NutritionEntity {
    return NutritionEntity(
        productId = productId,
        kcal100 = kcal100,
        protein100 = protein100,
        fat100 = fat100,
        carbs100 = carbs100,
        sugar100 = sugar100,
        salt100 = salt100,
        fiber100 = fiber100
    )
}

fun ProductWithNutrition.toDomain(category: Category?): Product {
    return Product(
        id = product.id,
        name = product.name,
        brand = product.brand,
        category = category,
        barcode = product.barcode,
        imageUrl = product.imageUrl,
        countryTags = product.countryTags?.split(",")?.mapNotNull { tag ->
            val trimmed = tag.trim()
            if (trimmed.isBlank()) null else trimmed
        } ?: emptyList(),
        source = product.source.toSource(),
        nutrition = nutrition?.toDomain(),
        createdAt = product.createdAt,
        updatedAt = product.updatedAt
    )
}

fun Product.toEntity(categoryId: String?): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        brand = brand,
        categoryId = categoryId,
        barcode = barcode,
        imageUrl = imageUrl,
        countryTags = if (countryTags.isEmpty()) null else countryTags.joinToString(","),
        source = source.name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun PriceQuoteEntity.toDomain(): PriceQuote {
    return PriceQuote(
        productId = productId,
        storeId = storeId,
        regionKey = regionKey,
        currency = currency,
        avgPrice = avgPrice,
        source = source,
        updatedAt = updatedAt,
        isMock = isMock
    )
}

fun PriceQuote.toEntity(): PriceQuoteEntity {
    return PriceQuoteEntity(
        productId = productId,
        storeId = storeId ?: "unknown",
        regionKey = regionKey,
        currency = currency,
        avgPrice = avgPrice,
        source = source,
        updatedAt = updatedAt,
        isMock = isMock
    )
}

private fun String.toSource(): Product.Source {
    return Product.Source.entries.firstOrNull { it.name == this }
        ?: Product.Source.OTHER
}
