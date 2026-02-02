package com.example.foodmind.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.foodmind.di.MainDispatcher
import com.example.foodmind.domain.model.FoodItem
import com.example.foodmind.domain.usecase.GetFoodItemByIdUseCase
import com.example.foodmind.navigation.Screen
import com.example.foodmind.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the food detail screen.
 */
@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFoodItemByIdUseCase: GetFoodItemByIdUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : BaseViewModel<FoodDetailUiState, FoodDetailUiEvent>(
    initialState = FoodDetailUiState(),
    dispatcher = mainDispatcher
) {

    private val foodId: String = checkNotNull(savedStateHandle[Screen.FoodDetail.ARG_FOOD_ID])

    init {
        loadFoodItem()
    }

    private fun loadFoodItem() {
        viewModelScope.launch(mainDispatcher) {
            updateState { it.copy(isLoading = true, errorMessage = null) }
            val result = getFoodItemByIdUseCase(foodId)
            result.fold(
                onSuccess = { item ->
                    if (item == null) {
                        updateState {
                            it.copy(
                                isLoading = false,
                                errorMessage = "Food item not found."
                            )
                        }
                    } else {
                        updateState { it.copy(isLoading = false, item = item) }
                    }
                },
                onFailure = { error ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Unable to load food item."
                        )
                    }
                }
            )
        }
    }
}

data class FoodDetailUiState(
    val isLoading: Boolean = true,
    val item: FoodItem? = null,
    val errorMessage: String? = null
)

sealed class FoodDetailUiEvent
