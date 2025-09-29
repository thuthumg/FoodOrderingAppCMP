package org.ttm.foodorderingappcmp.features.orders.order_list.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR

@Composable
fun FoodOrderingAppOrdersScreen(modifier: Modifier,onTapItem: ()-> Unit) {
    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            OrderTopAppBar()
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM)){
            items(3){
                PastOrderItemRow(
                    onTapItem = {

                    }
                )
            }
        }

    }
}

@Preview
@Composable
fun FoodOrderingAppOrdersScreenPreview() {
    FoodOrderingAppOrdersScreen(modifier = Modifier, onTapItem = {})
}