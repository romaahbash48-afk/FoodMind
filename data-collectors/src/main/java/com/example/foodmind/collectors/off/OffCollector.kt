package com.example.foodmind.collectors.off

import com.example.foodmind.collectors.util.RateLimiter
import org.json.JSONObject

class OffCollector(
    private val client: OpenFoodFactsClient = OpenFoodFactsClient(),
    private val rateLimiter: RateLimiter = RateLimiter()
) {
    suspend fun fetchCategory(
        categoryTag: String,
        pages: Int,
        pageSize: Int,
        countryTag: String? = null
    ): List<OffProduct> {
        val results = mutableListOf<OffProduct>()
        for (page in 1..pages) {
            rateLimiter.throttle()
            val products = client.fetchProducts(categoryTag, page, pageSize, countryTag)
            products.mapNotNullTo(results) { product ->
                parseProduct(product)
            }
        }
        return results
    }

    private fun parseProduct(product: JSONObject): OffProduct? {
        val name = product.optString("product_name")
            .ifBlank { product.optString("generic_name") }
            .trim()
        if (name.isBlank()) return null

        val brand = product.optString("brands")
            .split(",")
            .map { it.trim() }
            .firstOrNull { it.isNotBlank() }

        val barcode = product.optString("code").ifBlank { null }
        val imageUrl = product.optString("image_url")
            .ifBlank { product.optString("image_front_url") }
            .ifBlank { null }

        val countries = product.optJSONArray("countries_tags")
        val countryTags = if (countries == null) {
            emptyList()
        } else {
            (0 until countries.length())
                .mapNotNull { index ->
                    val value = countries.optString(index)
                    if (value.isNullOrBlank()) null else value
                }
        }

        val nutrition = parseNutrition(product.optJSONObject("nutriments"))

        return OffProduct(
            name = name,
            brand = brand,
            barcode = barcode,
            imageUrl = imageUrl,
            countryTags = countryTags,
            nutrition = nutrition
        )
    }

    private fun parseNutrition(nutriments: JSONObject?): OffNutrition? {
        if (nutriments == null) return null
        val kcal = readEnergyKcal(nutriments)
        val protein = readDouble(nutriments, "proteins_100g", "proteins_100ml")
        val fat = readDouble(nutriments, "fat_100g", "fat_100ml")
        val carbs = readDouble(nutriments, "carbohydrates_100g", "carbohydrates_100ml")
        val sugar = readDouble(nutriments, "sugars_100g", "sugars_100ml")
        val salt = readDouble(nutriments, "salt_100g", "salt_100ml")
        val fiber = readDouble(nutriments, "fiber_100g", "fiber_100ml")

        if (kcal == null && protein == null && fat == null && carbs == null) {
            return null
        }

        return OffNutrition(
            kcal100 = kcal ?: 0.0,
            protein100 = protein ?: 0.0,
            fat100 = fat ?: 0.0,
            carbs100 = carbs ?: 0.0,
            sugar100 = sugar,
            salt100 = salt,
            fiber100 = fiber
        )
    }

    private fun readEnergyKcal(nutriments: JSONObject): Double? {
        val direct = readDouble(nutriments, "energy-kcal_100g", "energy-kcal_100ml")
        if (direct != null) return direct
        val energyKj = readDouble(nutriments, "energy_100g", "energy_100ml")
        return energyKj?.times(KJ_TO_KCAL)
    }

    private fun readDouble(nutriments: JSONObject, vararg keys: String): Double? {
        for (key in keys) {
            if (!nutriments.has(key)) continue
            val value = nutriments.optDouble(key, Double.NaN)
            if (!value.isNaN() && value.isFinite()) {
                return value
            }
        }
        return null
    }

    private companion object {
        const val KJ_TO_KCAL = 0.239005736
    }
}
