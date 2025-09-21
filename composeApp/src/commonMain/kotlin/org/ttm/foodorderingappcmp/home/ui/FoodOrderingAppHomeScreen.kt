package org.ttm.foodorderingappcmp.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodOrderingAppHomeScreen() {
    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            TopAppBar({},
                navigationIcon = {},
                actions = {})
        },
        bottomBar = {},
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.Center){

        }
    }
}


@Preview
@Composable
fun FoodOrderingAppHomeScreenPreview() {
    FoodOrderingAppHomeScreen()
}