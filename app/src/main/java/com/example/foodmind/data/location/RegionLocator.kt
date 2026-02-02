package com.example.foodmind.data.location

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.annotation.SuppressLint
import com.example.foodmind.di.IoDispatcher
import com.example.foodmind.domain.model.Region
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegionLocator @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    @SuppressLint("MissingPermission")
    suspend fun detectRegion(): Region? = withContext(ioDispatcher) {
        val location = getLastKnownLocation() ?: return@withContext null
        val geocoder = Geocoder(context, Locale.getDefault())
        return@withContext try {
            val results = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            val address = results?.firstOrNull() ?: return@withContext null
            val country = address.countryName ?: return@withContext null
            val city = address.locality ?: address.subAdminArea
            val regionKey = buildRegionKey(country, city)
            Region(country = country, city = city, regionKey = regionKey)
        } catch (e: IOException) {
            null
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation(): Location? {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            ?: manager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
            ?: manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }

    private fun buildRegionKey(country: String, city: String?): String {
        val normalizedCity = city?.trim()?.lowercase()?.replace(" ", "-")
        return if (normalizedCity.isNullOrBlank()) {
            country.trim().lowercase()
        } else {
            "${country.trim().lowercase()}-$normalizedCity"
        }
    }
}
