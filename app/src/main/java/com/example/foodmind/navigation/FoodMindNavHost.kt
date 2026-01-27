package com.example.foodmind.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodmind.presentation.screens.home.HomeScreen

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
                onNavigate = { destination ->
                    navController.navigate(destination.route)
                }
            )
        }

        // Add more navigation destinations here as the app grows
        composable(route = Screen.Example.route) {
            // Example screen composable will go here
        }
    }
}
