package com.example.foodmind.collectors.retail

class AldiNordConnector : RetailerConnector {
    override val retailerName: String = "ALDI Nord"
    override val domain: String = "https://www.aldi-nord.de"
    override val decision: RetailerDecision = RetailerDecision.NEED_PARTNERSHIP

    override suspend fun fetchCatalog(): ConnectorResult<List<RetailerProduct>> {
        return ConnectorResult.NotAllowed("No explicit ToS permission; partnership required.")
    }

    override suspend fun fetchProduct(retailerProductId: String): ConnectorResult<RetailerProduct> {
        return ConnectorResult.NotAllowed("No explicit ToS permission; partnership required.")
    }

    override suspend fun fetchPrices(): ConnectorResult<List<RetailerPrice>> {
        return ConnectorResult.NotAllowed("No explicit ToS permission; partnership required.")
    }
}
