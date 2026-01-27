package io.foodmind.core.util

/**
 * Application-wide constants
 */
object Constants {
    const val DATABASE_NAME = "foodmind_database"
    const val PREFERENCES_NAME = "foodmind_preferences"
    
    // Network
    const val NETWORK_TIMEOUT = 30L // seconds
    const val BASE_URL = "https://api.foodmind.io/" // Placeholder URL
    
    // Pagination
    const val DEFAULT_PAGE_SIZE = 20
    
    // Cache
    const val CACHE_TIMEOUT = 5 * 60 * 1000L // 5 minutes in milliseconds
}
