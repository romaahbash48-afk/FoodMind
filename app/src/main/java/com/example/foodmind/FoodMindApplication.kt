package com.example.foodmind

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Main application class for FoodMind.
 * Annotated with @HiltAndroidApp to enable Hilt dependency injection.
 */
@HiltAndroidApp
class FoodMindApplication : Application()
