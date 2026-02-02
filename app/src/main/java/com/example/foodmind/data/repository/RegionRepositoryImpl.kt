package com.example.foodmind.data.repository

import android.content.Context
import com.example.foodmind.domain.model.Region
import com.example.foodmind.domain.repository.RegionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegionRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : RegionRepository {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val regionFlow = MutableStateFlow(loadRegion())

    init {
        prefs.registerOnSharedPreferenceChangeListener { _, _ ->
            regionFlow.value = loadRegion()
        }
    }

    override fun observeRegion(): Flow<Region?> = regionFlow.asStateFlow()

    override suspend fun setRegion(region: Region) {
        prefs.edit()
            .putString(KEY_COUNTRY, region.country)
            .putString(KEY_CITY, region.city)
            .putString(KEY_REGION, region.regionKey)
            .apply()
    }

    override suspend fun clearRegion() {
        prefs.edit()
            .remove(KEY_COUNTRY)
            .remove(KEY_CITY)
            .remove(KEY_REGION)
            .apply()
    }

    private fun loadRegion(): Region? {
        val country = prefs.getString(KEY_COUNTRY, null) ?: return null
        val city = prefs.getString(KEY_CITY, null)
        val regionKey = prefs.getString(KEY_REGION, null)
            ?: buildRegionKey(country, city)
        return Region(
            country = country,
            city = city,
            regionKey = regionKey
        )
    }

    private fun buildRegionKey(country: String, city: String?): String {
        val normalizedCity = city?.trim()?.lowercase()?.replace(" ", "-")
        return if (normalizedCity.isNullOrBlank()) {
            country.trim().lowercase()
        } else {
            "${country.trim().lowercase()}-$normalizedCity"
        }
    }

    private companion object {
        const val PREFS_NAME = "foodmind_region"
        const val KEY_COUNTRY = "region_country"
        const val KEY_CITY = "region_city"
        const val KEY_REGION = "region_key"
    }
}
