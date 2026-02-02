package com.example.foodmind.presentation.screens.home

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.foodmind.data.importer.ImportScheduler
import com.example.foodmind.di.MainDispatcher
import com.example.foodmind.domain.model.Category
import com.example.foodmind.domain.model.Product
import com.example.foodmind.domain.model.Region
import com.example.foodmind.domain.usecase.ObserveCategoriesUseCase
import com.example.foodmind.domain.usecase.ObserveProductsUseCase
import com.example.foodmind.domain.usecase.ObserveRegionUseCase
import com.example.foodmind.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the catalog (Home) screen.
 * Manages search, filters, and product loading.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeProductsUseCase: ObserveProductsUseCase,
    private val observeCategoriesUseCase: ObserveCategoriesUseCase,
    private val observeRegionUseCase: ObserveRegionUseCase,
    @ApplicationContext private val context: Context,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : BaseViewModel<HomeUiState, HomeUiEvent>(
    initialState = HomeUiState(),
    dispatcher = mainDispatcher
) {
    private val queryFlow = MutableStateFlow("")
    private val categoryFlow = MutableStateFlow<String?>(null)
    private var importScheduled = false

    init {
        observeCategories()
        observeProducts()
        observeRegion()
    }

    private fun observeCategories() {
        viewModelScope.launch(mainDispatcher) {
            observeCategoriesUseCase().collectLatest { categories ->
                val resolvedCategory = currentState.selectedCategoryId
                    ?.takeIf { selectedId -> categories.any { it.id == selectedId } }
                updateState {
                    it.copy(
                        categories = categories,
                        selectedCategoryId = resolvedCategory
                    )
                }
            }
        }
    }

    private fun observeProducts() {
        viewModelScope.launch(mainDispatcher) {
            combine(queryFlow, categoryFlow) { query, categoryId ->
                query to categoryId
            }.flatMapLatest { (query, categoryId) ->
                observeProductsUseCase(query, categoryId)
            }.collectLatest { products ->
                val loading = products.isEmpty() && currentState.region != null
                updateState {
                    it.copy(
                        isLoading = loading,
                        products = products,
                        errorMessage = null
                    )
                }
            }
        }
    }

    private fun observeRegion() {
        viewModelScope.launch(mainDispatcher) {
            observeRegionUseCase().collectLatest { region ->
                updateState { it.copy(region = region) }
                if (region != null && !importScheduled) {
                    importScheduled = true
                    ImportScheduler.scheduleCatalogImport(context, region)
                }
            }
        }
    }

    /**
     * Handles user actions from the UI
     */
    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnProductClick -> sendEvent(HomeUiEvent.NavigateToProductDetail(action.productId))
            is HomeAction.OnSearchQueryChange -> updateSearchQuery(action.query)
            is HomeAction.OnCategorySelected -> updateCategory(action.categoryId)
            is HomeAction.OnClearFilters -> clearFilters()
        }
    }

    private fun updateSearchQuery(query: String) {
        queryFlow.value = query
        updateState { it.copy(searchQuery = query) }
    }

    private fun updateCategory(categoryId: String?) {
        categoryFlow.value = categoryId
        updateState { it.copy(selectedCategoryId = categoryId) }
    }

    private fun clearFilters() {
        queryFlow.value = ""
        categoryFlow.value = null
        updateState { it.copy(searchQuery = "", selectedCategoryId = null) }
    }
}

/**
 * Represents the UI state of the Home screen
 */
data class HomeUiState(
    val isLoading: Boolean = true,
    val products: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedCategoryId: String? = null,
    val searchQuery: String = "",
    val region: Region? = null,
    val errorMessage: String? = null
)

/**
 * Represents one-time events that should trigger UI actions
 */
sealed class HomeUiEvent {
    data class NavigateToProductDetail(val productId: String) : HomeUiEvent()
}

/**
 * Represents user actions on the Home screen
 */
sealed class HomeAction {
    data class OnProductClick(val productId: String) : HomeAction()
    data class OnSearchQueryChange(val query: String) : HomeAction()
    data class OnCategorySelected(val categoryId: String?) : HomeAction()
    data object OnClearFilters : HomeAction()
}
