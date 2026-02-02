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
        val regionKey = inputData.getString(KEY_REGION_KEY)
        val currency = inputData.getString(KEY_CURRENCY)
        entryPoint.importCoordinator().seedCatalogIfEmpty(
            countryTag = countryTag,
            regionKey = regionKey,
            currency = currency
        )
        return Result.success()
    }

    companion object {
        const val KEY_COUNTRY_TAG = "countryTag"
        const val KEY_REGION_KEY = "regionKey"
        const val KEY_CURRENCY = "currency"
        const val WORK_NAME = "foodmind-catalog-import"
    }
}
