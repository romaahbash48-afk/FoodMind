package com.example.foodmind.data.importer

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ImportCoordinatorEntryPoint {
    fun importCoordinator(): ImportCoordinator
}
