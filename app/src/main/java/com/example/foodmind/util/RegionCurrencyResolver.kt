package com.example.foodmind.util

import java.util.Locale

object RegionCurrencyResolver {
    fun currencyFor(country: String): String {
        val normalized = country.trim().lowercase(Locale.US)
        return currencyOverrides[normalized] ?: "USD"
    }

    private val currencyOverrides = mapOf(
        "russia" to "RUB",
        "russian federation" to "RUB",
        "united states" to "USD",
        "canada" to "CAD",
        "united kingdom" to "GBP",
        "germany" to "EUR",
        "france" to "EUR",
        "italy" to "EUR",
        "spain" to "EUR",
        "poland" to "PLN",
        "ukraine" to "UAH",
        "turkey" to "TRY",
        "japan" to "JPY",
        "china" to "CNY",
        "india" to "INR",
        "brazil" to "BRL",
        "mexico" to "MXN"
    )
}
