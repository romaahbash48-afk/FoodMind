package io.foodmind.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.foodmind.domain.model.Food
import io.foodmind.domain.usecase.GetAllFoodsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the Home screen
 */
data class HomeUiState(
    val foods: List<Food> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel for the Home screen.
 * Demonstrates dependency injection of use case and state management with StateFlow.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllFoodsUseCase: GetAllFoodsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadFoods()
    }
    
    /**
     * Load foods from the repository via use case
     */
    private fun loadFoods() {
        viewModelScope.launch {
            getAllFoodsUseCase()
                .catch { e ->
                    _uiState.value = HomeUiState(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred"
                    )
                }
                .collect { foods ->
                    _uiState.value = HomeUiState(
                        foods = foods,
                        isLoading = false
                    )
                }
        }
    }
    
    /**
     * Refresh the food list
     */
    fun refresh() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        loadFoods()
    }
}
