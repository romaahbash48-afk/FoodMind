package com.example.foodmind.dataimport

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class OpenFoodFactsClient(
    private val baseUrl: String = "https://world.openfoodfacts.org"
) {
    suspend fun fetchProducts(
        categoryTag: String,
        page: Int,
        pageSize: Int,
        countryTag: String?
    ): List<JSONObject> {
        val url = buildUrl(categoryTag, page, pageSize, countryTag)
        val response = fetch(url)
        val json = JSONObject(response)
        val products = json.optJSONArray("products") ?: JSONArray()
        return (0 until products.length())
            .mapNotNull { index ->
                val item = products.optJSONObject(index)
                if (item == null) null else item
            }
    }

    private fun buildUrl(
        categoryTag: String,
        page: Int,
        pageSize: Int,
        countryTag: String?
    ): String {
        val encodedCategory = URLEncoder.encode(categoryTag, StandardCharsets.UTF_8.name())
        val encodedCountry = countryTag?.let {
            URLEncoder.encode(it, StandardCharsets.UTF_8.name())
        }
        return if (encodedCountry == null) {
            "$baseUrl/category/$encodedCategory.json?page=$page&page_size=$pageSize"
        } else {
            "$baseUrl/cgi/search.pl?action=process" +
                "&tagtype_0=categories&tag_contains_0=contains&tag_0=$encodedCategory" +
                "&tagtype_1=countries&tag_contains_1=contains&tag_1=$encodedCountry" +
                "&page=$page&page_size=$pageSize&json=1"
        }
    }

    private suspend fun fetch(url: String): String = withContext(Dispatchers.IO) {
        val connection = (URL(url).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = 15000
            readTimeout = 20000
        }
        try {
            BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                reader.readText()
            }
        } finally {
            connection.disconnect()
        }
    }
}
