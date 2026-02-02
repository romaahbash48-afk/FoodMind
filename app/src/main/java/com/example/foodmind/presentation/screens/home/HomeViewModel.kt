package com.example.foodmind.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.example.foodmind.di.MainDispatcher
import com.example.foodmind.domain.model.FoodItem
import com.example.foodmind.domain.usecase.GetFoodItemsUseCase
import com.example.foodmind.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the catalog (Home) screen.
 * Manages search, filters, and food catalog loading.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFoodItemsUseCase: GetFoodItemsUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : BaseViewModel<HomeUiState, HomeUiEvent>(
    initialState = HomeUiState(),
    dispatcher = mainDispatcher
) {

    init {
        loadFoodItems()
    }

    private fun loadFoodItems() {
        viewModelScope.launch(mainDispatcher) {
            updateState { it.copy(isLoading = true, errorMessage = null) }
            val result = getFoodItemsUseCase()
            result.fold(
                onSuccess = { foodFlow ->
                    foodFlow.collectLatest { items ->
                        val categories = items.map { it.category }.distinct().sorted()
                        val resolvedCategory = currentState.selectedCategory?.takeIf { it in categories }
                        val updated = currentState.copy(
                            isLoading = false,
                            items = items,
                            categories = categories,
                            selectedCategory = resolvedCategory,
                            errorMessage = null
                        )
                        updateState { applyFilters(updated) }
                    }
                },
                onFailure = { error ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Unable to load food catalog."
                        )
                    }
                }
            )
        }
    }

    /**
     * Handles user actions from the UI
     */
    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnFoodClick -> sendEvent(HomeUiEvent.NavigateToFoodDetail(action.foodId))
            is HomeAction.OnSearchQueryChange -> updateSearchQuery(action.query)
            is HomeAction.OnCategorySelected -> updateCategory(action.category)
            is HomeAction.OnClearFilters -> clearFilters()
        }
    }

    private fun updateSearchQuery(query: String) {
        val updated = currentState.copy(searchQuery = query)
        updateState { applyFilters(updated) }
    }

    private fun updateCategory(category: String?) {
        val updated = currentState.copy(selectedCategory = category)
        updateState { applyFilters(updated) }
    }

    private fun clearFilters() {
        val updated = currentState.copy(searchQuery = "", selectedCategory = null)
        updateState { applyFilters(updated) }
    }

    private fun applyFilters(state: HomeUiState): HomeUiState {
        val query = state.searchQuery.trim().lowercase()
        val filtered = state.items.filter { item ->
            val matchesCategory = state.selectedCategory == null || item.category == state.selectedCategory
            val matchesQuery = query.isEmpty() ||
                item.name.lowercase().contains(query) ||
                item.description.lowercase().contains(query)
            matchesCategory && matchesQuery
        }
        return state.copy(filteredItems = filtered)
    }
}

/**
 * Represents the UI state of the Home screen
 */
data class HomeUiState(
    val isLoading: Boolean = true,
    val items: List<FoodItem> = emptyList(),
    val filteredItems: List<FoodItem> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val searchQuery: String = "",
    val errorMessage: String? = null
)

/**
 * Represents one-time events that should trigger UI actions
 */
sealed class HomeUiEvent {
    data class NavigateToFoodDetail(val foodId: String) : HomeUiEvent()
}

/**
 * Represents user actions on the Home screen
 */
sealed class HomeAction {
    data class OnFoodClick(val foodId: String) : HomeAction()
    data class OnSearchQueryChange(val query: String) : HomeAction()
    data class OnCategorySelected(val category: String?) : HomeAction()
    data object OnClearFilters : HomeAction()
}
