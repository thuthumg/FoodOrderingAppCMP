package org.ttm.foodorderingappcmp.orders.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR

@Composable
fun FoodOrderingAppOrdersScreen(modifier: Modifier) {
    Scaffold(containerColor = SCREEN_BG_COLOR) {
        Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()){
            Text("Orders Screen")
        }
    }
}

@Preview
@Composable
fun FoodOrderingAppOrdersScreenPreview() {
    FoodOrderingAppOrdersScreen(modifier = Modifier)
}