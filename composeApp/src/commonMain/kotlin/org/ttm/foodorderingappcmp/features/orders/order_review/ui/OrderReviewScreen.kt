package org.ttm.foodorderingappcmp.features.orders.order_review.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.confirm_order
import foodorderingappcmp.composeapp.generated.resources.credit_card
import foodorderingappcmp.composeapp.generated.resources.delivery_address
import foodorderingappcmp.composeapp.generated.resources.order_summary
import foodorderingappcmp.composeapp.generated.resources.order_total
import foodorderingappcmp.composeapp.generated.resources.payment_method
import foodorderingappcmp.composeapp.generated.resources.review_order
import foodorderingappcmp.composeapp.generated.resources.total
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.ttm.foodorderingappcmp.common.ui.CommonAlertDialog
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppButton
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppTopAppBar
import org.ttm.foodorderingappcmp.common.ui.LoadingDialog
import org.ttm.foodorderingappcmp.core.MARGIN_LARGE
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_3X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.features.orders.order_review.state.OrderReviewState
import org.ttm.foodorderingappcmp.features.orders.order_review.viewmodel.OrderReviewViewModel
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

@Composable
fun OrderReviewRoute(viewModel: OrderReviewViewModel,
                     onTapBack: () -> Unit,
                     onNavigateToOrderConfirmation: () -> Unit) {


    val orderReviewState by viewModel.orderReviewState.collectAsStateWithLifecycle()

    OrderReviewScreen(
        orderReviewState = orderReviewState,
        onTapBack = onTapBack,
        onTapConfirmOrder = { paymentId, deliveryAddressId, foodItemList ->
            viewModel.submitOrder(paymentId,deliveryAddressId,foodItemList)
        },
        onDismissErrorAlertDialog = {
            viewModel.onDismissErrorAlertDialog()
        },
        onNavigateToOrderConfirmation = onNavigateToOrderConfirmation
    )

}
@Composable
fun OrderReviewScreen(
    orderReviewState: OrderReviewState,
    onTapBack: () -> Unit,
    onTapConfirmOrder: (Long, Long,  List<FoodItemVO>) -> Unit,
    onDismissErrorAlertDialog: () -> Unit,
    onNavigateToOrderConfirmation: () -> Unit) {


    /************* Loading State *********************/
    if (orderReviewState.loading) {
        LoadingDialog(
            onDismissRequest = {}
        )
    }

    /************* API Call Error State *********************/
    if (orderReviewState.message.isNotBlank() && (orderReviewState.errorDialogShowStatus)) {

        CommonAlertDialog(
            title = "Error",
            message = orderReviewState.message,
            onConfirm = {
                onDismissErrorAlertDialog()

            }
        )
    }
    /************* API Call Success State *********************/
    if(orderReviewState.orderSubmitStatus){
        onNavigateToOrderConfirmation()
    }

    /********************** Order Review Screen *******************************/
    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            FoodOrderingAppTopAppBar(
                stringResource(Res.string.review_order),
                onTapBack = {
                    onTapBack()
                })
        }
    ) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {

            LazyColumn(
                contentPadding = PaddingValues(bottom = 88.dp)
            ) {

                //title
                item {
                    //title section
                    Text(
                        stringResource(Res.string.order_summary),
                        fontSize = TEXT_REGULAR_3X,
                        color = TITLE_BLACK_COLOR,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            top = MARGIN_LARGE,
                            start = MARGIN_MEDIUM_2,
                            bottom = MARGIN_MEDIUM
                        )
                    )
                }

                //order item list
                items(orderReviewState.shoppingCartList.size) { index ->
                    OrderItemRow(foodItemVO = orderReviewState.shoppingCartList[index])

                }


                //Delivery Address
                item {
                    //Delivery address  section
                    Column {
                        Text(
                            stringResource(Res.string.delivery_address),
                            fontSize = TEXT_REGULAR_3X,
                            color = TITLE_BLACK_COLOR,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                top = MARGIN_LARGE,
                                start = MARGIN_MEDIUM_2,
                                bottom = MARGIN_MEDIUM
                            )
                        )

                        Text(
                            "Home",
                            fontSize = TEXT_REGULAR_3X,
                            color = TITLE_BLACK_COLOR,
                            modifier = Modifier.padding(
                                top = MARGIN_LARGE,
                                start = MARGIN_MEDIUM_2,
                                bottom = MARGIN_MEDIUM
                            )
                        )
                        Text(
                            orderReviewState.deliveryAddressAndPaymentVO?.deliveryAddress?.streetAddress ?: "-",
                            fontSize = TEXT_REGULAR_2X,
                            color = Color(135, 99, 99),
                            modifier = Modifier.padding(
                                start = MARGIN_MEDIUM_2,
                                bottom = MARGIN_MEDIUM
                            )
                        )
                    }

                }

                //Payment method
                item {
                    //Payment method section
                    Column {
                        Text(
                            stringResource(Res.string.payment_method),
                            fontSize = TEXT_REGULAR_3X,
                            color = TITLE_BLACK_COLOR,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                top = MARGIN_LARGE,
                                start = MARGIN_MEDIUM_2,
                                bottom = MARGIN_MEDIUM
                            )
                        )

                        Row(
                            modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2
                            ),
                            horizontalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),
                            verticalAlignment = Alignment.CenterVertically

                        )
                        {
                            Image(
                                painterResource(Res.drawable.credit_card),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(50.dp).clip(
                                    RoundedCornerShape(MARGIN_MEDIUM)
                                )
                            )
                            Column {
                                Text(
                                    "Credit Card",
                                    fontSize = TEXT_REGULAR_3X,
                                    color = TITLE_BLACK_COLOR,
                                    modifier = Modifier.padding(
                                        top = MARGIN_LARGE,
                                        start = MARGIN_MEDIUM_2,
                                        bottom = MARGIN_MEDIUM
                                    )
                                )
                                Text(
                                    orderReviewState.deliveryAddressAndPaymentVO?.paymentMethod?.formatCardNumber() ?: "-",
                                    fontSize = TEXT_REGULAR_2X,
                                    color = TITLE_BLACK_COLOR,
                                    modifier = Modifier.padding(
                                        start = MARGIN_MEDIUM_2,
                                        bottom = MARGIN_MEDIUM
                                    )
                                )
                            }
                        }

                    }

                }

                //Order Total
                item {
                    //Order Total section
                    Column {
                        Text(
                            stringResource(Res.string.order_total),
                            fontSize = TEXT_REGULAR_3X,
                            color = TITLE_BLACK_COLOR,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                top = MARGIN_LARGE,
                                start = MARGIN_MEDIUM_2,
                                bottom = MARGIN_MEDIUM
                            )
                        )

                        Row(
                            modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2,
                                vertical = MARGIN_MEDIUM_2
                            ),
                            horizontalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),
                            verticalAlignment = Alignment.CenterVertically

                        )
                        {
                            Text(
                                stringResource(Res.string.total),
                                fontSize = TEXT_REGULAR_2X,
                                color = Color(135, 99, 99),
                                modifier = Modifier.weight(1f)

                            )
                            Text(
                                "$${orderReviewState.shoppingCartList.sumOf { it.getItemPrice()}}",
                                fontSize = TEXT_REGULAR_2X,
                                color = Color(135, 99, 99),
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.End

                            )
                        }

                    }

                }

            }

            //Confirm Order
            Box(
                modifier = Modifier.fillMaxWidth().background(
                    color = SCREEN_BG_COLOR
                ).align(Alignment.BottomCenter)
            ){

                FoodOrderingAppButton(
                    onTapButton = {

                        onTapConfirmOrder(
                              orderReviewState.deliveryAddressAndPaymentVO?.paymentMethod?.id ?: -1,
                              orderReviewState.deliveryAddressAndPaymentVO?.deliveryAddress?.id ?: -1,
                            orderReviewState.shoppingCartList
                        )
                    },
                    modifier =
                        Modifier
                            .padding(
                                horizontal = MARGIN_MEDIUM_2,
                                vertical = MARGIN_MEDIUM_2
                            )
                            .fillMaxWidth()

                            .height(48.dp),
                    btnText = stringResource(Res.string.confirm_order),
                    fontSize = TEXT_REGULAR_2X
                )
            }


        }

    }
}

//@Preview
//@Composable
//fun OrderReviewScreenPreview() {
//    OrderReviewScreen(onTapBack = {}, onTapConfirmOrder = {})
//}