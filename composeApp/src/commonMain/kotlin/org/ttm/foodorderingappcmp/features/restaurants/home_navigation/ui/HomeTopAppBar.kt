package org.ttm.foodorderingappcmp.features.restaurants.home_navigation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.home_screen_title
import foodorderingappcmp.composeapp.generated.resources.ic_shopping_card
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_LARGE
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.core.TOP_APP_BAR_ICON_SIZE


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeTopAppBar(onTapShoppingCart:() -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SCREEN_BG_COLOR
        ),
        title = {
            Text(
                stringResource(Res.string.home_screen_title),
                color = TITLE_BLACK_COLOR,
                fontSize = TEXT_LARGE,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {},
        actions = {
            Icon(
                painterResource(Res.drawable.ic_shopping_card),
                contentDescription = null,
                modifier = Modifier.size(TOP_APP_BAR_ICON_SIZE).offset(
                    x = -MARGIN_MEDIUM
                ).clickable{
                    onTapShoppingCart()
                }
            )
        })
}