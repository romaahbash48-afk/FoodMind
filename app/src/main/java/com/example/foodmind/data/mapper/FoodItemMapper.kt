package com.example.foodmind.data.mapper

import com.example.foodmind.data.model.FoodItemDto
import com.example.foodmind.domain.model.FoodItem

/**
 * Mapper between data layer DTOs and domain models.
 * Keeps the layers separated and independent.
 */

/**
 * Converts DTO to domain model.
 */
fun FoodItemDto.toDomain(): FoodItem {
    return FoodItem(
        id = id,
        name = name,
        description = description,
        calories = calories,
        imageUrl = imageUrl
    )
}

/**
 * Converts domain model to DTO.
 */
fun FoodItem.toDto(): FoodItemDto {
    return FoodItemDto(
        id = id,
        name = name,
        description = description,
        calories = calories,
        imageUrl = imageUrl
    )
}

/**
 * Converts list of DTOs to domain models.
 */
fun List<FoodItemDto>.toDomain(): List<FoodItem> {
    return map { it.toDomain() }
}
