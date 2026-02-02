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
     * Food detail screen - shows full nutrition and ratings
     */
    data object FoodDetail : Screen("food_detail/{foodId}") {
        const val ARG_FOOD_ID = "foodId"

        fun createRoute(foodId: String): String {
            return "food_detail/$foodId"
        }
    }
}
