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
import com.example.foodmind.presentation.screens.region.RegionSelectionScreen

/**
 * Main navigation graph for the FoodMind app.
 * Defines all navigation routes and their corresponding composable screens.
 */
@Composable
fun FoodMindNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.RegionSelection.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.RegionSelection.route) {
            RegionSelectionScreen(
                onRegionReady = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.RegionSelection.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Home.route) {
            HomeScreen(
                onProductSelected = { foodId ->
                    navController.navigate(Screen.FoodDetail.createRoute(foodId))
                },
                onRegionSelect = {
                    navController.navigate(Screen.RegionSelection.route)
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
