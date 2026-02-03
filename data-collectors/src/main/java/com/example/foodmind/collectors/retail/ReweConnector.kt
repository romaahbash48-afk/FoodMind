package com.example.foodmind.collectors.retail

class ReweConnector : RetailerConnector {
    override val retailerName: String = "REWE"
    override val domain: String = "https://www.rewe.de"
    override val decision: RetailerDecision = RetailerDecision.DENY

    override suspend fun fetchCatalog(): ConnectorResult<List<RetailerProduct>> {
        return ConnectorResult.NotAllowed("Robots.txt blocks shop/search; ToS not accessible.")
    }

    override suspend fun fetchProduct(retailerProductId: String): ConnectorResult<RetailerProduct> {
        return ConnectorResult.NotAllowed("Robots.txt blocks shop/search; ToS not accessible.")
    }

    override suspend fun fetchPrices(): ConnectorResult<List<RetailerPrice>> {
        return ConnectorResult.NotAllowed("Robots.txt blocks shop/search; ToS not accessible.")
    }
}
