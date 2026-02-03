package com.example.foodmind.presentation.screens.detail

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.foodmind.domain.model.PriceQuote
import com.example.foodmind.domain.model.Product
import java.util.Locale

/**
 * Detail screen for a selected food item.
 */
@Composable
fun FoodDetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: FoodDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val errorMessage = state.errorMessage
    val product = state.product

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Button(
            onClick = onNavigateBack,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
            product != null -> {
                FoodDetailContent(
                    item = product,
                    regionLabel = state.region?.let { region ->
                        region.city?.let { "${region.country}, $it" } ?: region.country
                    },
                    priceQuote = state.priceQuote
                )
            }
        }
    }
}

@Composable
private fun FoodDetailContent(
    item: Product,
    regionLabel: String?,
    priceQuote: PriceQuote?
) {
    val context = LocalContext.current
    val shape = RoundedCornerShape(16.dp)
    val nutrition = item.nutrition

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(item.imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = item.name,
        contentScale = ContentScale.Crop,
        placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
        error = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )

    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = item.name,
        style = MaterialTheme.typography.headlineSmall
    )
    item.brand?.let { brand ->
        Text(
            text = brand,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
    Text(
        text = item.category?.name ?: "Uncategorized",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Text(
        text = "Source: ${formatSource(item.source)}",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Barcode: ${item.barcode ?: "N/A"}",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(16.dp))
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Nutrition per 100g/ml",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            if (nutrition == null) {
                Text(
                    text = "Nutrition data not available.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                NutritionRow(label = "Calories", value = "${formatMacro(nutrition.kcal100)} kcal")
                NutritionRow(label = "Protein", value = "${formatMacro(nutrition.protein100)} g")
                NutritionRow(label = "Carbs", value = "${formatMacro(nutrition.carbs100)} g")
                NutritionRow(label = "Fat", value = "${formatMacro(nutrition.fat100)} g")
                nutrition.fiber100?.let {
                    NutritionRow(label = "Fiber", value = "${formatMacro(it)} g")
                }
                nutrition.sugar100?.let {
                    NutritionRow(label = "Sugar", value = "${formatMacro(it)} g")
                }
                nutrition.salt100?.let {
                    NutritionRow(label = "Salt", value = "${formatMacro(it)} g")
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Average price",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (priceQuote == null) {
                Text(
                    text = "Нет данных для региона ${regionLabel ?: "выбранного"}.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Text(
                    text = "${formatPrice(priceQuote)} ${priceQuote.currency}",
                    style = MaterialTheme.typography.bodyLarge
                )
                if (priceQuote.isMock) {
                    Text(
                        text = "Price is approximate (mock).",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "Source: ${priceQuote.source}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun NutritionRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    Spacer(modifier = Modifier.height(6.dp))
}

private fun formatMacro(value: Double): String {
    return String.format(Locale.US, "%.1f", value)
}

private fun formatPrice(quote: PriceQuote): String {
    return String.format(Locale.US, "%.2f", quote.avgPrice)
}

private fun formatSource(source: Product.Source): String {
    return when (source) {
        Product.Source.OPEN_FOOD_FACTS -> "Open Food Facts"
        Product.Source.RETAILER_FEED -> "Retailer feed"
        Product.Source.OTHER -> "Other"
    }
}
