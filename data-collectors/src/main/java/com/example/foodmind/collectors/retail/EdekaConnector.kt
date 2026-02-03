package com.example.foodmind.collectors.retail

class EdekaConnector : RetailerConnector {
    override val retailerName: String = "EDEKA"
    override val domain: String = "https://www.edeka.de"
    override val decision: RetailerDecision = RetailerDecision.DENY

    override suspend fun fetchCatalog(): ConnectorResult<List<RetailerProduct>> {
        return ConnectorResult.NotAllowed("Robots.txt disallows catalog/search; no ToS permission.")
    }

    override suspend fun fetchProduct(retailerProductId: String): ConnectorResult<RetailerProduct> {
        return ConnectorResult.NotAllowed("Robots.txt disallows catalog/search; no ToS permission.")
    }

    override suspend fun fetchPrices(): ConnectorResult<List<RetailerPrice>> {
        return ConnectorResult.NotAllowed("Robots.txt disallows catalog/search; no ToS permission.")
    }
}
