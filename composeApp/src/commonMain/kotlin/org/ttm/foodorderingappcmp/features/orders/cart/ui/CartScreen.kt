package org.ttm.foodorderingappcmp.features.orders.cart.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.cart
import foodorderingappcmp.composeapp.generated.resources.empty_cart
import foodorderingappcmp.composeapp.generated.resources.order_now
import foodorderingappcmp.composeapp.generated.resources.place_order
import foodorderingappcmp.composeapp.generated.resources.total
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.ttm.foodorderingappcmp.common.ui.ErrorAlertDialog
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppButton
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppTopAppBar
import org.ttm.foodorderingappcmp.common.ui.LoadingDialog
import org.ttm.foodorderingappcmp.core.BUTTON_BG_COLOR
import org.ttm.foodorderingappcmp.core.MARGIN_CARD_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_LARGE
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_XXLARGE
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_LARGE
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.features.orders.cart.state.CartState
import org.ttm.foodorderingappcmp.features.orders.cart.viewmodel.CartViewModel
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

@Composable
fun CartRoute(
    cartViewModel: CartViewModel,
    onTapBack: () -> Unit,
    onTapPlaceOrder: () -> Unit,
    onTapOrderNow: () -> Unit,
) {
    val cartState by cartViewModel.cartState.collectAsStateWithLifecycle()

    if (cartState.loading) {
        LoadingDialog(
            onDismissRequest = {}
        )
    }

    if (cartState.message.isNotBlank() && !(cartState.dismissStatus)) {

        ErrorAlertDialog(
            showDialog = true,
            title = "Error",
            message = cartState.message,
            onDismiss = {
                cartViewModel.onDismissErrorAlertDialog()

            }
        )
    }

    if(cartState.showRemoveItemDialog){
        AlertDialog(
            onDismissRequest = { cartViewModel.onDismissRemoveItemDialog() },
            title = { Text("Remove item?") },
            text = { Text("Are you sure you want to remove this food from your cart?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        cartState.removeItem?.let {
                            cartViewModel.deleteCart(it)
                        }

                    }
                ) {
                    Text("Remove")
                }
            },
            dismissButton = {
                TextButton(onClick = { cartViewModel.onDismissRemoveItemDialog() }) {
                    Text("Cancel")
                }
            }
        )
    }


    CartScreen(
        cartState = cartState,
        onTapBack = onTapBack,
        onTapPlaceOrder = onTapPlaceOrder,
        onTapOrderNow = onTapOrderNow,
        onIncrease = {foodItemVO ->
            cartViewModel.onIncreaseItemQty(foodItemVO)
        },
        onDecrease = {foodItemVO ->
            cartViewModel.onDecreaseItemQty(foodItemVO)
        }

    )
}

@Composable
fun CartScreen(
    cartState: CartState,
    onTapBack: () -> Unit,
    onTapPlaceOrder: () -> Unit,
    onTapOrderNow: () -> Unit,
    onIncrease: (FoodItemVO) -> Unit,
    onDecrease: (FoodItemVO) -> Unit
) {

    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            FoodOrderingAppTopAppBar(
                stringResource(Res.string.cart),
                onTapBack = {
                    onTapBack()
                })
        }
    ) { innerPadding ->

        if(cartState.foodItemList.isEmpty())
        {
            EmptyCartSection(innerPadding, onTapOrderNow)
        }else{
            CartListSection(
                cartState = cartState,
                innerPadding,
                onTapPlaceOrder,
                onIncrease = {
                    onIncrease(it)
                },
                onDecrease = {
                    onDecrease(it)
                }

            )
        }



    }
}

@Composable
private fun CartListSection(
    cartState: CartState,
    innerPadding: PaddingValues,
    onTapPlaceOrder: () -> Unit,
    onIncrease: (FoodItemVO) -> Unit,
    onDecrease: (FoodItemVO) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(innerPadding).fillMaxSize()
    ) {

        //Selected Food Item
        items(cartState.foodItemList.size) { index ->
            CartItemRow(
                foodItemVO = cartState.foodItemList[index],
                onIncrease = {
                    onIncrease(it)
                },
                onDecrease = {
                    onDecrease(it)
                }

            )

        }

        //Divider
        item {
            HorizontalDivider(
                modifier = Modifier.padding(
                    horizontal = MARGIN_MEDIUM_2,
                    vertical = MARGIN_MEDIUM_2
                )
                    .height(3.dp),
                color = Color(217, 217, 217)

            )
        }

        //Total Price
        item {
            cartState.foodItemList
            TotalPriceSection(
                totalPrice = "$${cartState.foodItemList.sumOf { it.getItemPrice()}}"
            )
        }


        //Place Order
        item {
            PlaceOrderSection(
                onTapPlaceOrder = {
                    onTapPlaceOrder()
                }
            )
        }


    }
}

@Composable
private fun EmptyCartSection(
    innerPadding: PaddingValues,
    onTapOrderNow: () -> Unit,
) {
    Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
        Column(modifier = Modifier.padding(top = MARGIN_XXLARGE)) {
            Icon(
                painterResource(Res.drawable.empty_cart),
                contentDescription = null,
                modifier = Modifier.size(75.dp).align(Alignment.CenterHorizontally)
            )
            Text(
                "There are no items in your cart yet. Add meals from a restaurant to get started.",
                fontSize = TEXT_REGULAR,
                color = Color.Gray,
                modifier = Modifier.padding(
                    top = MARGIN_LARGE,
                    start = MARGIN_MEDIUM_2,
                    end = MARGIN_MEDIUM_2,
                    bottom = MARGIN_MEDIUM
                ),
                textAlign = TextAlign.Center
            )

            OutlinedButton(
                onClick = { onTapOrderNow() },
                shape = RoundedCornerShape(MARGIN_MEDIUM),
                border = BorderStroke(1.dp, BUTTON_BG_COLOR), // outline color
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent, // transparent background
                    contentColor = BUTTON_BG_COLOR // text & icon color
                ),
                modifier = Modifier.padding(
                    horizontal = MARGIN_MEDIUM_2,
                    vertical = MARGIN_CARD_MEDIUM_2
                ).height(38.dp).align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(Res.string.order_now),
                    fontSize = TEXT_REGULAR_2X,
                    textAlign = TextAlign.Center,
                    color = Color.Red
                )
            }

        }

    }
}


@Composable
private fun PlaceOrderSection(onTapPlaceOrder: () -> Unit) {
    FoodOrderingAppButton(
        onTapButton = {
            onTapPlaceOrder()
        },
        modifier =
            Modifier.padding(
                horizontal = MARGIN_MEDIUM_2,
                vertical = MARGIN_CARD_MEDIUM_2
            )
                .fillMaxWidth().height(48.dp),
        btnText = stringResource(Res.string.place_order),
        fontSize = TEXT_REGULAR_2X
    )
}

@Composable
private fun TotalPriceSection(totalPrice: String) {
    Row(modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_MEDIUM_2)) {
        Text(
            stringResource(Res.string.total),
            color = TITLE_BLACK_COLOR,
            fontSize = TEXT_LARGE,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            totalPrice,
            color = TITLE_BLACK_COLOR,
            fontSize = TEXT_LARGE,
            fontWeight = FontWeight.Bold
        )
    }
}


//@Preview
//@Composable
//fun CartScreenPreview() {
//    CartScreen(onTapBack = {}, onTapPlaceOrder = {}, onTapOrderNow = {})
//}