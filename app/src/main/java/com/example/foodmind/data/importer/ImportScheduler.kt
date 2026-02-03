package com.example.foodmind.data.importer

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.foodmind.domain.model.Region
import com.example.foodmind.util.RegionTagResolver

object ImportScheduler {
    fun scheduleCatalogImport(context: Context, region: Region?) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val countryTag = region?.let { RegionTagResolver.countryTag(it.country) }
        val request = OneTimeWorkRequestBuilder<CatalogImportWorker>()
            .setConstraints(constraints)
            .setInputData(
                workDataOf(
                    CatalogImportWorker.KEY_COUNTRY_TAG to countryTag
                )
            )
            .build()
        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                CatalogImportWorker.WORK_NAME,
                ExistingWorkPolicy.KEEP,
                request
            )
    }
}
