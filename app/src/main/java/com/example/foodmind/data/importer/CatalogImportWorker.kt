package com.example.foodmind.data.importer

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.hilt.android.EntryPointAccessors

class CatalogImportWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        return try {
            val entryPoint = EntryPointAccessors.fromApplication(
                applicationContext,
                ImportCoordinatorEntryPoint::class.java
            )
            val countryTag = inputData.getString(KEY_COUNTRY_TAG)
            entryPoint.importCoordinator().seedCatalogIfEmpty(
                countryTag = countryTag
            )
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < MAX_RETRIES) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

    companion object {
        const val KEY_COUNTRY_TAG = "countryTag"
        const val WORK_NAME = "foodmind-catalog-import"
        private const val MAX_RETRIES = 3
    }
}
