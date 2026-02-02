package com.example.foodmind.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.foodmind.domain.model.FoodItem
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale

/**
 * Home screen composable.
 * The main entry point of the FoodMind app.
 */
@Composable
fun HomeScreen(
    onFoodSelected: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is HomeUiEvent.NavigateToFoodDetail -> onFoodSelected(event.foodId)
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
            .padding(16.dp)
    ) {
        Text(
            text = "Food Catalog",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Explore nutrition facts, taste ratings, and product images.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { onAction(HomeAction.OnSearchQueryChange(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search products") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))
        CategoryRow(
            categories = state.categories,
            selectedCategory = state.selectedCategory,
            onCategorySelected = { onAction(HomeAction.OnCategorySelected(it)) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            state.errorMessage != null -> {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            else -> {
                Text(
                    text = "${state.filteredItems.size} items",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (state.filteredItems.isEmpty()) {
                    Text(
                        text = "No items match your filters.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    FoodList(
                        items = state.filteredItems,
                        onFoodClick = { onAction(HomeAction.OnFoodClick(it)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryRow(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = { Text("All") }
            )
        }
        items(categories) { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = { Text(category) }
            )
        }
    }
}

@Composable
private fun FoodList(
    items: List<FoodItem>,
    onFoodClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items, key = { it.id }) { item ->
            FoodItemCard(
                item = item,
                onClick = { onFoodClick(item.id) }
            )
        }
    }
}

@Composable
private fun FoodItemCard(
    item: FoodItem,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FoodImage(
                name = item.name,
                imageUrl = item.imageUrl
            )
            Spacer(modifier = Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = item.category,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = nutritionSummary(item),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "Taste rating: ${formatRating(item.tasteRating)} / 5",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun FoodImage(
    name: String,
    imageUrl: String?
) {
    val initials = name.take(1).uppercase(Locale.US)
    val shape = RoundedCornerShape(12.dp)
    val context = LocalContext.current
    if (imageUrl.isNullOrBlank()) {
        Box(
            modifier = Modifier
                .size(88.dp)
                .clip(shape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    } else {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            error = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier
                .size(88.dp)
                .clip(shape)
        )
    }
}

private fun nutritionSummary(item: FoodItem): String {
    val nutrition = item.nutrition
    return "${nutrition.calories} kcal • " +
        "P ${formatMacro(nutrition.proteinGrams)}g • " +
        "C ${formatMacro(nutrition.carbsGrams)}g • " +
        "F ${formatMacro(nutrition.fatGrams)}g"
}

private fun formatMacro(value: Double): String {
    return String.format(Locale.US, "%.1f", value)
}

private fun formatRating(value: Double): String {
    return String.format(Locale.US, "%.1f", value)
}
