package com.example.foodmind.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Base class for all use cases in the domain layer.
 * Provides a clean way to execute business logic with proper coroutine handling.
 * 
 * @param R The return type of the use case
 * @param P The parameter type of the use case
 */
abstract class BaseUseCase<in P, out R>(
    private val dispatcher: CoroutineDispatcher
) {
    /**
     * Executes the use case with the given parameters.
     * Runs on the specified dispatcher.
     */
    suspend operator fun invoke(params: P): Result<R> = withContext(dispatcher) {
        try {
            Result.success(execute(params))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * The actual business logic implementation.
     * To be overridden by concrete use case classes.
     */
    @Throws(Exception::class)
    protected abstract suspend fun execute(params: P): R
}
