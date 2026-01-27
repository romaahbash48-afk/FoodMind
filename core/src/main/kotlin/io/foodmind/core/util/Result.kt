package io.foodmind.core.util

/**
 * A generic wrapper around a value that represents the result of an operation.
 * This is useful for handling success and error states in a type-safe way.
 */
sealed class Result<out T> {
    /**
     * Represents a successful result with data
     */
    data class Success<T>(val data: T) : Result<T>()
    
    /**
     * Represents an error result with an exception
     */
    data class Error(val exception: Throwable) : Result<Nothing>()
    
    /**
     * Represents a loading state
     */
    object Loading : Result<Nothing>()
}

/**
 * Extension function to get data if Result is Success, otherwise null
 */
fun <T> Result<T>.getOrNull(): T? {
    return when (this) {
        is Result.Success -> data
        else -> null
    }
}

/**
 * Extension function to check if Result is Success
 */
fun <T> Result<T>.isSuccess(): Boolean = this is Result.Success

/**
 * Extension function to check if Result is Error
 */
fun <T> Result<T>.isError(): Boolean = this is Result.Error

/**
 * Extension function to check if Result is Loading
 */
fun <T> Result<T>.isLoading(): Boolean = this is Result.Loading
