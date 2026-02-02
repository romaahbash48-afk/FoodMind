package com.example.foodmind.util

import java.util.Locale

object RegionTagResolver {
    fun countryTag(country: String): String {
        val normalized = country.trim().lowercase(Locale.US)
        val mapped = countryTagOverrides[normalized] ?: normalized.replace(" ", "-")
        return "en:$mapped"
    }

    private val countryTagOverrides = mapOf(
        "united states" to "united-states",
        "united kingdom" to "united-kingdom",
        "russian federation" to "russia",
        "czech republic" to "czech-republic",
        "south korea" to "south-korea"
    )
}
