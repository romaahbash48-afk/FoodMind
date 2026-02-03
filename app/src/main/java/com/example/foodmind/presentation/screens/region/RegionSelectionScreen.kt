package com.example.foodmind.presentation.screens.region

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RegionSelectionScreen(
    onRegionReady: () -> Unit,
    viewModel: RegionSelectionViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val errorMessage = state.errorMessage
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        viewModel.onAction(RegionSelectionAction.OnPermissionResult(granted))
    }

    LaunchedEffect(state.region) {
        if (state.region != null) {
            onRegionReady()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Select your region",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "We use region data to show local prices (approximate).",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Use my location")
        }

        if (state.isDetecting) {
            Spacer(modifier = Modifier.height(12.dp))
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Or enter manually",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = state.countryInput,
            onValueChange = { viewModel.onAction(RegionSelectionAction.OnCountryChange(it)) },
            label = { Text("Country") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.cityInput,
            onValueChange = { viewModel.onAction(RegionSelectionAction.OnCityChange(it)) },
            label = { Text("City (optional)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { viewModel.onAction(RegionSelectionAction.OnSaveManual) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save region")
        }

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
