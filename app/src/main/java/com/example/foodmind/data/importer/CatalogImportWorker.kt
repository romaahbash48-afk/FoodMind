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
        val entryPoint = EntryPointAccessors.fromApplication(
            applicationContext,
            ImportCoordinatorEntryPoint::class.java
        )
        val countryTag = inputData.getString(KEY_COUNTRY_TAG)
        entryPoint.importCoordinator().seedCatalogIfEmpty(
            countryTag = countryTag
        )
        return Result.success()
    }

    companion object {
        const val KEY_COUNTRY_TAG = "countryTag"
        const val WORK_NAME = "foodmind-catalog-import"
    }
}
