package com.example.foodmind.collectors.util

import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class RateLimiter(
    private val minimumDelay: Duration = 250.milliseconds
) {
    private var lastRequestTime = 0L

    suspend fun throttle() {
        val now = System.currentTimeMillis()
        val elapsed = now - lastRequestTime
        val waitFor = minimumDelay.inWholeMilliseconds - elapsed
        if (waitFor > 0) {
            delay(waitFor)
        }
        lastRequestTime = System.currentTimeMillis()
    }
}
