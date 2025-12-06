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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.ttm.foodorderingappcmp.common.ui.CommonAlertDialog
import org.ttm.foodorderingappcmp.common.ui.DeliveryPaymentDialog
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
import org.ttm.foodorderingappcmp.features.orders.cart.actions.CartActions
import org.ttm.foodorderingappcmp.features.orders.cart.events.CartEvents
import org.ttm.foodorderingappcmp.features.orders.cart.state.CartState
import org.ttm.foodorderingappcmp.features.orders.cart.viewmodel.CartViewModel
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

@Composable
fun CartRoute(
    cartViewModel: CartViewModel,
    onTapBack: () -> Unit,
    onNavigateToCheckout: () -> Unit,
    onTapOrderNow: () -> Unit,
    onNavigateToReviewOrder: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val cartState by cartViewModel.cartState.collectAsStateWithLifecycle()


    LaunchedEffect(Unit){
        cartViewModel.navigationSharedFlow.collectLatest { events ->
            when(events){
                is CartEvents.OnNavigateToCheckOut -> {
                    onNavigateToCheckout()
                }

                is CartEvents.OnNavigateToDetail -> {
                    onTapBack()
                }
                is CartEvents.OnNavigateToHome -> {
                    onTapOrderNow()
                }
                is CartEvents.OnNavigateToLogin -> {
                    onNavigateToLogin()
                }
                is CartEvents.OnNavigateToReviewOrder -> {
                    onNavigateToReviewOrder()
                }
            }
        }
    }



    CartScreen(
        cartState = cartState,
        onAction = {
            cartViewModel.onAction(it)
        }
    )


}

@Composable
fun CartScreen(
    cartState: CartState,
    onAction: (CartActions) -> Unit) {

    /*************Loading State*********************/
    if (cartState.loading) {
        LoadingDialog(
            onDismissRequest = {}
        )
    }

    /*************API Call Error State*********************/
    if (cartState.message.isNotBlank()) {

        CommonAlertDialog(
            title = "Error",
            message = cartState.message,
            onConfirm = {
                if(cartState.loginStatus){
                    onAction(CartActions.OnUnauthorizedDialogDismissed())
                }else{
                    onAction(CartActions.OnErrorDialogDismissed())
                }
            })
    }

    /*************Shopping Cart Item Remove State*********************/
    if (cartState.showRemoveItemDialog) {
        CommonAlertDialog(
            title ="Remove item?",
            message = "Are you sure you want to remove this food from your cart?",
            confirmText = "Remove",
            dismissText = "Cancel",
            onConfirm = {
                cartState.removeItem?.let {
                    onAction(CartActions.OnTapDeleteCart(it))
                }
            },
            onDismiss = {
                onAction(CartActions.OnRemoveItemDialogDismissed())
            }
        )
    }

    /*************Previously used Delivery Address and Payment Method Choose State*********************/
    if (cartState.showDeliveryPaymentDialog) {
        DeliveryPaymentDialog(
            deliveryAddressAndPaymentListVO = cartState.deliveryAddressAndPaymentListVO,
            onTapConfirm = { deliveryAddressVO, paymentVO ->
                onAction(CartActions.OnTapConfirm(deliveryAddressVO, paymentVO))
            },
            onTapAddNew = {
                onAction(CartActions.OnTapAddNew())
            },
            onTapBack = {
                onAction(CartActions.OnDeliveryPaymentDialogDismissed())
            }
        )
    }

    /****************** Shopping Cart Screen *******************************/
    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            FoodOrderingAppTopAppBar(
                stringResource(Res.string.cart),
                onTapBack = {
                    onAction(CartActions.OnTapBack())
                })
        }
    ) { innerPadding ->

        if (cartState.foodItemList.isEmpty()) {
            EmptyCartSection(innerPadding, {
                onAction(CartActions.OnTapOrder())
            })
        } else {
            CartListSection(
                cartState = cartState,
                innerPadding = innerPadding,
                onTapPlaceOrder = {
                    onAction(CartActions.OnTapPlaceOrder())
                },
                onIncrease = {
                    onAction(CartActions.OnTapIncreaseBtn(it))
                },
                onDecrease = {
                    onAction(CartActions.OnTapDecreaseBtn(it))
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
    onDecrease: (FoodItemVO) -> Unit,
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
                totalPrice = "$${cartState.foodItemList.sumOf { it.getItemPrice() }}"
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
                border = BorderStroke(1.dp, BUTTON_BG_COLOR),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = BUTTON_BG_COLOR
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