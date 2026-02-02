package com.example.foodmind.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.foodmind.di.MainDispatcher
import com.example.foodmind.domain.model.PriceQuote
import com.example.foodmind.domain.model.Product
import com.example.foodmind.domain.model.Region
import com.example.foodmind.domain.usecase.GetProductUseCase
import com.example.foodmind.domain.usecase.ObservePriceQuoteUseCase
import com.example.foodmind.domain.usecase.ObserveRegionUseCase
import com.example.foodmind.navigation.Screen
import com.example.foodmind.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the food detail screen.
 */
@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductUseCase: GetProductUseCase,
    private val observeRegionUseCase: ObserveRegionUseCase,
    private val observePriceQuoteUseCase: ObservePriceQuoteUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : BaseViewModel<FoodDetailUiState, FoodDetailUiEvent>(
    initialState = FoodDetailUiState(),
    dispatcher = mainDispatcher
) {

    private val productId: String = checkNotNull(savedStateHandle[Screen.FoodDetail.ARG_FOOD_ID])

    init {
        loadProduct()
        observeRegion()
        observePrice()
    }

    private fun loadProduct() {
        viewModelScope.launch(mainDispatcher) {
            updateState { it.copy(isLoading = true, errorMessage = null) }
            val result = getProductUseCase(productId)
            result.fold(
                onSuccess = { product ->
                    if (product == null) {
                        updateState {
                            it.copy(
                                isLoading = false,
                                errorMessage = "Product not found."
                            )
                        }
                    } else {
                        updateState { it.copy(isLoading = false, product = product) }
                    }
                },
                onFailure = { error ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Unable to load product."
                        )
                    }
                }
            )
        }
    }

    private fun observeRegion() {
        viewModelScope.launch(mainDispatcher) {
            observeRegionUseCase().collectLatest { region ->
                updateState { it.copy(region = region) }
            }
        }
    }

    private fun observePrice() {
        viewModelScope.launch(mainDispatcher) {
            observeRegionUseCase()
                .filterNotNull()
                .flatMapLatest { region ->
                    observePriceQuoteUseCase(productId, region.regionKey)
                }
                .collectLatest { quote ->
                    updateState { it.copy(priceQuote = quote) }
                }
        }
    }
}

data class FoodDetailUiState(
    val isLoading: Boolean = true,
    val product: Product? = null,
    val region: Region? = null,
    val priceQuote: PriceQuote? = null,
    val errorMessage: String? = null
)

sealed class FoodDetailUiEvent
