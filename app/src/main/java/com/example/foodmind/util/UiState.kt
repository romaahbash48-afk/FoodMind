package com.example.foodmind.util

/**
 * Sealed class representing the state of an async operation.
 * Useful for handling loading, success, and error states in the UI.
 */
sealed class UiState<out T> {
    /**
     * Initial state before any operation
     */
    data object Idle : UiState<Nothing>()

    /**
     * Loading state during an operation
     */
    data object Loading : UiState<Nothing>()

    /**
     * Success state with data
     */
    data class Success<T>(val data: T) : UiState<T>()

    /**
     * Error state with an error message
     */
    data class Error(val message: String, val throwable: Throwable? = null) : UiState<Nothing>()
}

/**
 * Extension function to check if state is loading
 */
fun <T> UiState<T>.isLoading(): Boolean = this is UiState.Loading

/**
 * Extension function to check if state is successful
 */
fun <T> UiState<T>.isSuccess(): Boolean = this is UiState.Success

/**
 * Extension function to check if state is error
 */
fun <T> UiState<T>.isError(): Boolean = this is UiState.Error

/**
 * Extension function to get data if successful, null otherwise
 */
fun <T> UiState<T>.getDataOrNull(): T? = (this as? UiState.Success)?.data
