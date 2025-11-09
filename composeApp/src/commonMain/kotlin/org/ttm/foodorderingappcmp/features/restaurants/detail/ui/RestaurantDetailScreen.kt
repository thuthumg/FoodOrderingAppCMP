package org.ttm.foodorderingappcmp.features.restaurants.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.image_not_supported
import foodorderingappcmp.composeapp.generated.resources.view_my_cart
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.ttm.foodorderingappcmp.common.ui.CommonAlertDialog
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppButton
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppTopAppBar
import org.ttm.foodorderingappcmp.common.ui.LoadingDialog
import org.ttm.foodorderingappcmp.common.ui.ShimmerBox
import org.ttm.foodorderingappcmp.core.MARGIN_CARD_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_LARGE
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO
import org.ttm.foodorderingappcmp.features.restaurants.detail.state.RestaurantDetailState
import org.ttm.foodorderingappcmp.features.restaurants.detail.viewmodel.RestaurantDetailViewModel


@Composable
fun RestaurantDetailRoute(
    restaurantViewModel: RestaurantDetailViewModel,
    onTapBack: () -> Unit,
    onTapViewMyCart: () -> Unit,
) {

    val restaurantDetailState by restaurantViewModel.restaurantDetailState.collectAsStateWithLifecycle()

    RestaurantDetailScreen(
        restaurantDetailState = restaurantDetailState,
        onTapBack = {
           onTapBack()
        },
        onTapViewMyCart = {
            onTapViewMyCart()
        },
        onTapAddToCart = { foodItemVO ->
            restaurantViewModel.addToCart(foodItemVO)
        },
        onDismissErrorAlertDialog = {
            restaurantViewModel.onDismissErrorAlertDialog()
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailScreen(
    restaurantDetailState: RestaurantDetailState,
    onTapBack: () -> Unit,
    onTapViewMyCart: () -> Unit,
    onTapAddToCart: (FoodItemVO) -> Unit,
    onDismissErrorAlertDialog: () -> Unit
) {

    val verticalScrollState = rememberLazyListState()
    val horizontalScrollState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    var selected by remember { mutableStateOf(0) }
    val tabs = restaurantDetailState.restaurantVO?.foodCategories?.map { it.name } ?: listOf()



    /************* Loading ********************/
    if (restaurantDetailState.loading) {
        LoadingDialog(
            onDismissRequest = {}
        )
    }

    /************* API Call Error ********************/
    if (restaurantDetailState.message.isNotBlank() && (restaurantDetailState.errorDialogShowStatus)) {
        CommonAlertDialog(
            title = "Error",
            message = restaurantDetailState.message,
            onConfirm = {
                onDismissErrorAlertDialog()
            }
        )
    }


//
//    val density = LocalDensity.current
//
//// e.g. 56.dp toolbar + 8.dp gap
//    val topOffsetDp = 56.dp + 8.dp
//    val topOffsetPx = with(density) { topOffsetDp.roundToPx() }
    val windowInfo = LocalWindowInfo.current.containerSize // unit => pixel
    val screenHeight = with(LocalDensity.current){ windowInfo.height.toDp().roundToPx()}

    var stickyHeaderHeightPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current
    val topOffsetPx = with(density){
        with(density) { stickyHeaderHeightPx.toDp() }.roundToPx()}

    /***************** Restaurant Detail Screen ********************************/
    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            //TopAppBar
            FoodOrderingAppTopAppBar(
                title = restaurantDetailState.restaurantVO?.name ?: "",
                onTapBack = {
                    onTapBack()
                })
        }
    ) { innerPadding ->


        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {

            LazyColumn(
                state = verticalScrollState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 88.dp)
            ) {
                item {
                    RestaurantImageSection(restaurantDetailState.restaurantVO?.imageUrl)
                }

                stickyHeader {
                    Surface(color = SCREEN_BG_COLOR, modifier = Modifier.onGloballyPositioned {
                        stickyHeaderHeightPx = it.size.height
                    }) {

                        CategoryTabListSection(
                            horizontalScrollState = horizontalScrollState,
                            modifier = Modifier.padding(top = MARGIN_MEDIUM_2),
                            tabs = tabs,
                            selectedIndex = selected,
                            onSelect = {
                                coroutineScope.launch {
                                    selected = it
                                    verticalScrollState.animateScrollToItem(selected + 2, -100)
                                    horizontalScrollState.animateScrollToItem(selected)
                                }

                            }
                        )


                    }
                }

                // items(sectionCount){
                restaurantDetailState.restaurantVO?.foodCategories?.let {
                    itemsIndexed(it.toList()) { index, category ->
                        Column {
                            ItemHeaderSection(
                                headerName = category.name,
                                modifier = Modifier.padding(top = MARGIN_LARGE)
                            )
                            (category.foodItems).forEach { foodItem->
                                ItemDetailSection(
                                    foodItem,
                                    onTapAddToCart = { it ->
                                        onTapAddToCart(foodItem)

                                    }
                                )
                            }
                        }
                    }
                }


            }

            if (restaurantDetailState.showViewMyCart) {
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
private fun RestaurantImageSection(imageUrl: String?) {
    SubcomposeAsyncImage(
        imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        loading = {
            // CircularProgressIndicator(modifier = Modifier.size(30.dp))
            ShimmerBox(Modifier.fillMaxSize())
        },
        error = {

            Box(
                modifier = Modifier
                    .background(Color.Gray.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.image_not_supported),
                    contentDescription = "Error loading image",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Black
                )
            }


        },
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


//@Preview
//@Composable
//fun RestaurantAndMenuScreenReview() {
//    RestaurantDetailScreen(onTapBack = {}, onTapViewMyCart = {})
//}