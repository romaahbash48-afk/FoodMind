package com.example.foodmind.data.remote

import com.example.foodmind.domain.model.Category
import com.example.foodmind.domain.model.PriceQuote
import com.example.foodmind.domain.model.Product
import com.example.foodmind.domain.model.Store
import com.example.foodmind.domain.model.StoreProductMapping
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupabaseSyncManager @Inject constructor(
    private val service: SupabaseService
) {
    suspend fun upsertCategories(categories: List<Category>) {
        if (categories.isEmpty() || !SupabaseConfig.isConfigured) return
        val payload = JSONArray()
        categories.forEach { category ->
            payload.put(
                JSONObject()
                    .put("id", category.id)
                    .put("name", category.name)
                    .put("parent_id", category.parentId)
            )
        }
        service.upsert("categories", payload, "id")
    }

    suspend fun upsertProducts(products: List<Product>) {
        if (products.isEmpty() || !SupabaseConfig.isConfigured) return
        val payload = JSONArray()
        products.forEach { product ->
            val json = JSONObject()
                .put("id", product.id)
                .put("name", product.name)
                .put("brand", product.brand)
                .put("category_id", product.category?.id)
                .put("barcode", product.barcode)
                .put("image_url", product.imageUrl)
                .put("created_at", product.createdAt)
                .put("updated_at", product.updatedAt)
            if (product.countryTags.isNotEmpty()) {
                val tags = JSONArray()
                product.countryTags.forEach { tags.put(it) }
                json.put("country_tags", tags)
            } else {
                json.put("country_tags", JSONArray())
            }
            payload.put(json)
        }
        service.upsert("products", payload, "id")
    }

    suspend fun upsertNutrition(products: List<Product>) {
        if (products.isEmpty() || !SupabaseConfig.isConfigured) return
        val payload = JSONArray()
        products.forEach { product ->
            val nutrition = product.nutrition ?: return@forEach
            payload.put(
                JSONObject()
                    .put("product_id", product.id)
                    .put("kcal_100", nutrition.kcal100)
                    .put("protein_100", nutrition.protein100)
                    .put("fat_100", nutrition.fat100)
                    .put("carbs_100", nutrition.carbs100)
                    .put("sugar_100", nutrition.sugar100)
                    .put("salt_100", nutrition.salt100)
                    .put("fiber_100", nutrition.fiber100)
            )
        }
        if (payload.length() > 0) {
            service.upsert("nutrition", payload, "product_id")
        }
    }

    suspend fun upsertPriceQuotes(quotes: List<PriceQuote>) {
        if (quotes.isEmpty() || !SupabaseConfig.isConfigured) return
        val payload = JSONArray()
        quotes.forEach { quote ->
            payload.put(
                JSONObject()
                    .put("product_id", quote.productId)
                    .put("store_id", quote.storeId)
                    .put("region_key", quote.regionKey)
                    .put("currency", quote.currency)
                    .put("avg_price", quote.avgPrice)
                    .put("source", quote.source)
                    .put("updated_at", quote.updatedAt)
                    .put("is_mock", quote.isMock)
            )
        }
        service.upsert("price_quotes", payload, "product_id,region_key,store_id")
    }

    suspend fun upsertStores(stores: List<Store>) {
        if (stores.isEmpty() || !SupabaseConfig.isConfigured) return
        val payload = JSONArray()
        stores.forEach { store ->
            payload.put(
                JSONObject()
                    .put("id", store.id)
                    .put("name", store.name)
                    .put("domain", store.domain)
            )
        }
        service.upsert("stores", payload, "id")
    }

    suspend fun upsertStoreProductMappings(mappings: List<StoreProductMapping>) {
        if (mappings.isEmpty() || !SupabaseConfig.isConfigured) return
        val payload = JSONArray()
        mappings.forEach { mapping ->
            payload.put(
                JSONObject()
                    .put("store_id", mapping.storeId)
                    .put("retailer_product_id", mapping.retailerProductId)
                    .put("product_id", mapping.productId)
            )
        }
        service.upsert("store_product_mappings", payload, "store_id,retailer_product_id")
    }
}
