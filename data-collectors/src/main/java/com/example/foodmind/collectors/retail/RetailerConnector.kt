package com.example.foodmind.collectors.retail

sealed class ConnectorResult<out T> {
    data class Success<T>(val data: T) : ConnectorResult<T>()
    data class NotAllowed(val reason: String) : ConnectorResult<Nothing>()
    data class Error(val message: String, val throwable: Throwable? = null) : ConnectorResult<Nothing>()
}

enum class RetailerDecision {
    ALLOW,
    DENY,
    NEED_PARTNERSHIP
}

data class RetailerProduct(
    val retailerProductId: String,
    val name: String,
    val brand: String?,
    val imageUrl: String?
)

data class RetailerPrice(
    val retailerProductId: String,
    val currency: String,
    val price: Double,
    val updatedAt: Long
)

interface RetailerConnector {
    val retailerName: String
    val domain: String
    val decision: RetailerDecision

    suspend fun fetchCatalog(): ConnectorResult<List<RetailerProduct>>
    suspend fun fetchProduct(retailerProductId: String): ConnectorResult<RetailerProduct>
    suspend fun fetchPrices(): ConnectorResult<List<RetailerPrice>>
}
