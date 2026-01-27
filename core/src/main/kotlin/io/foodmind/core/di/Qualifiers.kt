package io.foodmind.core.di

import javax.inject.Qualifier

/**
 * Qualifier for IO Dispatcher for Hilt DI
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IoDispatcher

/**
 * Qualifier for Main Dispatcher for Hilt DI
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainDispatcher

/**
 * Qualifier for Default Dispatcher for Hilt DI
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultDispatcher
