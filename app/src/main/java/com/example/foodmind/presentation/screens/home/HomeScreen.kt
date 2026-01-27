package com.example.foodmind.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodmind.navigation.Screen
import kotlinx.coroutines.flow.collectLatest

/**
 * Home screen composable.
 * The main entry point of the FoodMind app.
 */
@Composable
fun HomeScreen(
    onNavigate: (Screen) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    // Handle one-time events
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is HomeUiEvent.NavigateToExample -> {
                    onNavigate(Screen.Example)
                }
            }
        }
    }

    HomeScreenContent(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun HomeScreenContent(
    state: HomeUiState,
    onAction: (HomeAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            Text(
                text = state.greeting,
                style = MaterialTheme.typography.headlineMedium
            )
            
            Button(
                onClick = { onAction(HomeAction.OnExampleClick) },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Example Action")
            }
        }
    }
}
