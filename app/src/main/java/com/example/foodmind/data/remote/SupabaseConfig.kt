package com.example.foodmind.data.remote

import com.example.foodmind.BuildConfig

object SupabaseConfig {
    val url: String = BuildConfig.SUPABASE_URL
    val anonKey: String = BuildConfig.SUPABASE_ANON_KEY

    val isConfigured: Boolean
        get() = url.isNotBlank() && anonKey.isNotBlank()
}
