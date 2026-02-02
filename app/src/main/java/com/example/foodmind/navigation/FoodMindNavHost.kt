package com.example.foodmind.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.foodmind.presentation.screens.home.HomeScreen
import com.example.foodmind.presentation.screens.detail.FoodDetailScreen

/**
 * Main navigation graph for the FoodMind app.
 * Defines all navigation routes and their corresponding composable screens.
 */
@Composable
fun FoodMindNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onFoodSelected = { foodId ->
                    navController.navigate(Screen.FoodDetail.createRoute(foodId))
                }
            )
        }

        composable(
            route = Screen.FoodDetail.route,
            arguments = listOf(
                navArgument(Screen.FoodDetail.ARG_FOOD_ID) { type = NavType.StringType }
            )
        ) {
            FoodDetailScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
