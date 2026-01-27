package com.example.foodmind.navigation

/**
 * Sealed class representing all navigation destinations in the app.
 * Each destination has a route string used for navigation.
 */
sealed class Screen(val route: String) {
    /**
     * Home screen - main entry point of the app
     */
    data object Home : Screen("home")

    /**
     * Example screen - can be replaced with actual screens
     */
    data object Example : Screen("example")

    /**
     * Add more screens as needed following this pattern
     */
}
