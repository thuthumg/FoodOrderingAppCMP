package org.ttm.foodorderingappcmp.features.orders.order_list.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.orders
import org.jetbrains.compose.resources.stringResource
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_LARGE
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun OrderTopAppBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SCREEN_BG_COLOR
        ),
        title = {
            Text(
                stringResource(Res.string.orders),
                color = TITLE_BLACK_COLOR,
                fontSize = TEXT_LARGE,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {},
        actions = {

        })
}