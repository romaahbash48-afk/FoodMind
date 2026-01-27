package com.example.foodmind.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Base ViewModel providing common functionality for all ViewModels.
 * Handles state management and one-time events.
 * 
 * @param S The UI state type
 * @param E The UI event type
 */
abstract class BaseViewModel<S, E>(
    initialState: S,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    // UI State
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    // One-time events
    private val _uiEvent = MutableSharedFlow<E>()
    val uiEvent: SharedFlow<E> = _uiEvent.asSharedFlow()

    /**
     * Updates the current UI state
     */
    protected fun updateState(update: (S) -> S) {
        _uiState.value = update(_uiState.value)
    }

    /**
     * Sends a one-time UI event
     */
    protected fun sendEvent(event: E) {
        viewModelScope.launch(dispatcher) {
            _uiEvent.emit(event)
        }
    }

    /**
     * Gets the current state value
     */
    protected val currentState: S
        get() = _uiState.value
}
