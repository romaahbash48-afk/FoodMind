package io.foodmind

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for FoodMind.
 * Annotated with @HiltAndroidApp to enable Hilt dependency injection.
 */
@HiltAndroidApp
class FoodMindApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // Application initialization code here
    }
}
