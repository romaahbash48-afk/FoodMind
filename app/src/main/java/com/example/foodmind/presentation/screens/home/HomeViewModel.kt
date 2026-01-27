package com.example.foodmind.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.example.foodmind.di.MainDispatcher
import com.example.foodmind.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Home screen.
 * Manages the state and business logic for the home screen.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : BaseViewModel<HomeUiState, HomeUiEvent>(
    initialState = HomeUiState(),
    dispatcher = mainDispatcher
) {

    init {
        loadInitialData()
    }

    /**
     * Loads initial data for the home screen
     */
    private fun loadInitialData() {
        viewModelScope.launch(mainDispatcher) {
            updateState { it.copy(isLoading = false) }
        }
    }

    /**
     * Handles user actions from the UI
     */
    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnExampleClick -> handleExampleClick()
            // Add more actions as needed
        }
    }

    private fun handleExampleClick() {
        // Handle click logic
        sendEvent(HomeUiEvent.NavigateToExample)
    }
}

/**
 * Represents the UI state of the Home screen
 */
data class HomeUiState(
    val isLoading: Boolean = true,
    val greeting: String = "Welcome to FoodMind!"
)

/**
 * Represents one-time events that should trigger UI actions
 */
sealed class HomeUiEvent {
    data object NavigateToExample : HomeUiEvent()
}

/**
 * Represents user actions on the Home screen
 */
sealed class HomeAction {
    data object OnExampleClick : HomeAction()
}
