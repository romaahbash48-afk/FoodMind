package com.example.foodmind.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Base class for use cases that don't require parameters.
 * 
 * @param R The return type of the use case
 */
abstract class NoParamsUseCase<out R>(
    private val dispatcher: CoroutineDispatcher
) {
    /**
     * Executes the use case without parameters.
     */
    suspend operator fun invoke(): Result<R> = withContext(dispatcher) {
        try {
            Result.success(execute())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * The actual business logic implementation.
     */
    @Throws(Exception::class)
    protected abstract suspend fun execute(): R
}
