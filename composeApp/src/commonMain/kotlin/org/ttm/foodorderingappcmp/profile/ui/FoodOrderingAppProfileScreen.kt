package org.ttm.foodorderingappcmp.profile.ui

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
fun FoodOrderingAppProfileScreen(modifier: Modifier) {
    Scaffold(containerColor = SCREEN_BG_COLOR) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
            Text("Profile Screen")
        }
    }
}

@Preview
@Composable
fun FoodOrderingAppProfileScreenPreview() {
    FoodOrderingAppProfileScreen(modifier = Modifier)
}