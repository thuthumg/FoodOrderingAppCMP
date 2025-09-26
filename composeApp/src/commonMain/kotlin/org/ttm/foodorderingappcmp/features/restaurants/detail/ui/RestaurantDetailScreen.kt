package org.ttm.foodorderingappcmp.features.restaurants.detail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.restaurant_img
import foodorderingappcmp.composeapp.generated.resources.view_my_cart
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppButton
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppTopAppBar
import org.ttm.foodorderingappcmp.core.MARGIN_CARD_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_LARGE
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailScreen(
    onTapBack: () -> Unit,
    onTapViewMyCart: () -> Unit,
) {
    var showViewMyCart by rememberSaveable { mutableStateOf(false) }
    val sectionCount = 10
    val itemsPerSection = 2

    var selected by remember { mutableStateOf(0) }
    val tabs = listOf(
        "Featured",
        "Sides",
        "Popular",
        "All",
        "Featured",
        "Sides",
        "Popular",
        "All",
        "Featured",
        "Sides",
        "Popular",
        "All"
    )


    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            //TopAppBar
            FoodOrderingAppTopAppBar(
                title = "Burger Joint",
                onTapBack = {
                    onTapBack()
                })
        }
    ) { innerPadding ->


        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    RestaurantImageSection()
                }

                item {
                    CategoryTabListSection(
                        modifier = Modifier.padding(top = MARGIN_MEDIUM_2),
                        tabs = tabs,
                        selectedIndex = selected,
                        onSelect = { selected = it }
                    )

                }


                repeat(sectionCount) { sectionIndex ->
                    // Section header
                    item{
                        ItemHeaderSection(modifier = Modifier.padding(top = MARGIN_LARGE))
                    }

                    // Section Details
                    items(
                        itemsPerSection
                    ) { _ ->
                        ItemDetailSection(
                            showAddToCart = showViewMyCart,
                            onTapAddToCart = { it ->
                                showViewMyCart = it

                            }
                        )
                    }
                }
            }

            if (showViewMyCart) {
                ViewMyCartSection(
                    onTapViewMyCart = {
                        onTapViewMyCart()
                    }
                )

            }

        }

    }
}

@Composable
private fun RestaurantImageSection() {
    Image(
        painterResource(Res.drawable.restaurant_img),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth().height(200.dp)
    )
}

@Composable
private fun BoxScope.ViewMyCartSection(
    onTapViewMyCart: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth().background(color = SCREEN_BG_COLOR)
            .align(Alignment.BottomCenter)
    ) {
        FoodOrderingAppButton(
            onTapButton = {
                onTapViewMyCart()
            },
            modifier =
                Modifier.padding(
                    horizontal = MARGIN_MEDIUM_2,
                    vertical = MARGIN_CARD_MEDIUM_2
                )
                    .fillMaxWidth().height(48.dp),
            btnText = stringResource(Res.string.view_my_cart),
            fontSize = TEXT_REGULAR_2X
        )
    }
}


@Preview
@Composable
fun RestaurantAndMenuScreenReview() {
    RestaurantDetailScreen(onTapBack = {}, onTapViewMyCart = {})
}