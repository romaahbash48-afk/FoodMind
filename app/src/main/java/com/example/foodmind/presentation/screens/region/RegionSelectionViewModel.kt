package com.example.foodmind.presentation.screens.region

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.foodmind.data.importer.ImportScheduler
import com.example.foodmind.data.location.RegionLocator
import com.example.foodmind.di.MainDispatcher
import com.example.foodmind.domain.model.Region
import com.example.foodmind.domain.usecase.ObserveRegionUseCase
import com.example.foodmind.domain.usecase.SetRegionUseCase
import com.example.foodmind.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegionSelectionViewModel @Inject constructor(
    private val regionLocator: RegionLocator,
    private val observeRegionUseCase: ObserveRegionUseCase,
    private val setRegionUseCase: SetRegionUseCase,
    @ApplicationContext private val context: Context,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : BaseViewModel<RegionSelectionUiState, RegionSelectionUiEvent>(
    initialState = RegionSelectionUiState(),
    dispatcher = mainDispatcher
) {

    init {
        viewModelScope.launch(mainDispatcher) {
            observeRegionUseCase().collectLatest { region ->
                updateState { it.copy(region = region) }
                if (region != null) {
                    sendEvent(RegionSelectionUiEvent.NavigateToCatalog)
                }
            }
        }
    }

    fun onAction(action: RegionSelectionAction) {
        when (action) {
            is RegionSelectionAction.OnPermissionResult -> handlePermission(action.granted)
            is RegionSelectionAction.OnCountryChange -> updateState { it.copy(countryInput = action.value) }
            is RegionSelectionAction.OnCityChange -> updateState { it.copy(cityInput = action.value) }
            is RegionSelectionAction.OnSaveManual -> saveManualRegion()
            is RegionSelectionAction.OnDismissError -> updateState { it.copy(errorMessage = null) }
        }
    }

    private fun handlePermission(granted: Boolean) {
        if (!granted) {
            updateState { it.copy(errorMessage = "Location permission denied.") }
            return
        }
        viewModelScope.launch(mainDispatcher) {
            updateState { it.copy(isDetecting = true, errorMessage = null) }
            val region = regionLocator.detectRegion()
            if (region == null) {
                updateState {
                    it.copy(isDetecting = false, errorMessage = "Unable to detect region.")
                }
                return@launch
            }
            persistRegion(region)
        }
    }

    private fun saveManualRegion() {
        val country = currentState.countryInput.trim()
        if (country.isBlank()) {
            updateState { it.copy(errorMessage = "Country is required.") }
            return
        }
        val city = currentState.cityInput.trim().ifBlank { null }
        val regionKey = buildRegionKey(country, city)
        val region = Region(country = country, city = city, regionKey = regionKey)
        viewModelScope.launch(mainDispatcher) {
            persistRegion(region)
        }
    }

    private suspend fun persistRegion(region: Region) {
        val result = setRegionUseCase(region)
        result.fold(
            onSuccess = {
                updateState {
                    it.copy(isDetecting = false, errorMessage = null, region = region)
                }
                ImportScheduler.scheduleCatalogImport(context, region)
            },
            onFailure = { error ->
                updateState {
                    it.copy(
                        isDetecting = false,
                        errorMessage = error.message ?: "Failed to save region."
                    )
                }
            }
        )
    }

    private fun buildRegionKey(country: String, city: String?): String {
        val normalizedCity = city?.trim()?.lowercase()?.replace(" ", "-")
        return if (normalizedCity.isNullOrBlank()) {
            country.trim().lowercase()
        } else {
            "${country.trim().lowercase()}-$normalizedCity"
        }
    }
}

data class RegionSelectionUiState(
    val isDetecting: Boolean = false,
    val countryInput: String = "",
    val cityInput: String = "",
    val region: Region? = null,
    val errorMessage: String? = null
)

sealed class RegionSelectionUiEvent {
    data object NavigateToCatalog : RegionSelectionUiEvent()
}

sealed class RegionSelectionAction {
    data class OnPermissionResult(val granted: Boolean) : RegionSelectionAction()
    data class OnCountryChange(val value: String) : RegionSelectionAction()
    data class OnCityChange(val value: String) : RegionSelectionAction()
    data object OnSaveManual : RegionSelectionAction()
    data object OnDismissError : RegionSelectionAction()
}
