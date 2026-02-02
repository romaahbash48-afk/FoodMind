package com.example.foodmind.data.mapper

import com.example.foodmind.data.model.FoodItemDto
import com.example.foodmind.data.model.NutritionInfoDto
import com.example.foodmind.domain.model.FoodItem
import com.example.foodmind.domain.model.NutritionInfo

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
        category = category,
        nutrition = nutrition.toDomain(),
        tasteRating = tasteRating,
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
        category = category,
        nutrition = nutrition.toDto(),
        tasteRating = tasteRating,
        imageUrl = imageUrl
    )
}

/**
 * Converts list of DTOs to domain models.
 */
fun List<FoodItemDto>.toDomain(): List<FoodItem> {
    return map { it.toDomain() }
}

/**
 * Converts nutrition DTO to domain model.
 */
private fun NutritionInfoDto.toDomain(): NutritionInfo {
    return NutritionInfo(
        calories = calories,
        proteinGrams = proteinGrams,
        fatGrams = fatGrams,
        carbsGrams = carbsGrams,
        fiberGrams = fiberGrams,
        sugarGrams = sugarGrams,
        servingSizeGrams = servingSizeGrams
    )
}

/**
 * Converts nutrition domain model to DTO.
 */
private fun NutritionInfo.toDto(): NutritionInfoDto {
    return NutritionInfoDto(
        calories = calories,
        proteinGrams = proteinGrams,
        fatGrams = fatGrams,
        carbsGrams = carbsGrams,
        fiberGrams = fiberGrams,
        sugarGrams = sugarGrams,
        servingSizeGrams = servingSizeGrams
    )
}
