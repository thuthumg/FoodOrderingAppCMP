package org.ttm.foodorderingappcmp.features.orders.checkout.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.card_number
import foodorderingappcmp.composeapp.generated.resources.checkout
import foodorderingappcmp.composeapp.generated.resources.cvv
import foodorderingappcmp.composeapp.generated.resources.delivery_address
import foodorderingappcmp.composeapp.generated.resources.full_address
import foodorderingappcmp.composeapp.generated.resources.mm_yy
import foodorderingappcmp.composeapp.generated.resources.name_on_card
import foodorderingappcmp.composeapp.generated.resources.payment_details
import foodorderingappcmp.composeapp.generated.resources.place_order
import foodorderingappcmp.composeapp.generated.resources.save_for_future_use
import org.jetbrains.compose.resources.stringResource
import org.ttm.foodorderingappcmp.common.ui.CommonAlertDialog
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppButton
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppOutlineTxtField
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppTopAppBar
import org.ttm.foodorderingappcmp.common.ui.LoadingDialog
import org.ttm.foodorderingappcmp.core.BUTTON_SWITCH_COLOR
import org.ttm.foodorderingappcmp.core.MARGIN_LARGE
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_3X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.features.orders.checkout.state.CheckoutState
import org.ttm.foodorderingappcmp.features.orders.checkout.viewmodel.CheckoutViewModel

@Composable
fun CheckoutRoute(
    checkoutViewModel: CheckoutViewModel,
    onTapBack: () -> Unit,
    onNavigateToOrderReview: () -> Unit,
) {

    val checkoutState by checkoutViewModel.state.collectAsStateWithLifecycle()

    CheckoutScreen(
        state = checkoutState,
        onTapBack = {
            onTapBack()
        },
        onTapPlaceOrder = { cardNumber, expireDate, cvv, nameOnCard, deliveryAddress ->
            checkoutViewModel.addDeliveryAddressAndPayment(
                cardNumber,
                expireDate,
                cvv,
                nameOnCard,
                deliveryAddress
            )
        },
        onNavigateToOrderReview = {
            onNavigateToOrderReview()
        },
        onDismissErrorAlertDialog = {
            checkoutViewModel.onDismissErrorAlertDialog()
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    state: CheckoutState,
    onTapBack: () -> Unit,
    onTapPlaceOrder: (
        cardNumber: String,
        expireDate: String,
        cvv: String,
        nameOnCard: String,
        deliveryAddress: String,
    ) -> Unit,
    onNavigateToOrderReview: () -> Unit,
    onDismissErrorAlertDialog: () -> Unit

) {

    var cardNumber by remember { mutableStateOf("") }
    var mm_yy by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var nameOnCard by remember { mutableStateOf("") }
    var fullAddress by remember { mutableStateOf("") }
    var saveForFutureUse by remember { mutableStateOf(false) }


    /*************Loading State*********************/
    if (state.loading) {
        LoadingDialog(
            onDismissRequest = {}
        )
    }


    /*************API Call Success State*********************/
    if(state.checkoutApiStatus){
        state.deliveryAddressAndPaymentVO?.let {
            onNavigateToOrderReview()
        }
    }


        /*************API Call Error State*********************/
        if (state.message.isNotBlank() && (state.errorDialogShowStatus)) {
            CommonAlertDialog(
                title = "Error",
                message = state.message,
                onConfirm = {
                    onDismissErrorAlertDialog()
                }
            )
        }



    /***************** Checkout Screen ***********************/
    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            FoodOrderingAppTopAppBar(
                stringResource(Res.string.checkout),
                onTapBack = {
                    onTapBack()
                })
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding).fillMaxSize(),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM_2)

            ) {

                //title section
                Text(
                    stringResource(Res.string.payment_details),
                    fontSize = TEXT_REGULAR_3X,
                    color = TITLE_BLACK_COLOR,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        top = MARGIN_LARGE,
                        start = MARGIN_MEDIUM_2,
                        bottom = MARGIN_MEDIUM
                    )
                )

                //Card number input section
                FoodOrderingAppOutlineTxtField(
                    value = cardNumber,
                    onValueChange = { text ->
                        cardNumber = text
                    },
                    txt = stringResource(Res.string.card_number),
                    isPasswordField = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                    onImeAction = { },
                    modifier = Modifier
                        .padding(horizontal = MARGIN_MEDIUM_2)
                        .fillMaxWidth()
                )

                //mm_yy and cvv
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM_2)
                ) {
                    FoodOrderingAppOutlineTxtField(
                        value = mm_yy,
                        onValueChange = { text ->
                            mm_yy = text
                        },
                        txt = stringResource(Res.string.mm_yy),
                        isPasswordField = false,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        onImeAction = { },
                        modifier = Modifier
                            .padding(start = MARGIN_MEDIUM_2)
                            .weight(1f)
                    )

                    FoodOrderingAppOutlineTxtField(
                        value = cvv,
                        onValueChange = { text ->
                            cvv = text
                        },
                        txt = stringResource(Res.string.cvv),
                        isPasswordField = false,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                        onImeAction = { },
                        modifier = Modifier
                            .padding(end = MARGIN_MEDIUM_2)
                            .weight(1f)
                    )
                }

                // Name on Card
                FoodOrderingAppOutlineTxtField(

                    value = nameOnCard,
                    onValueChange = { text ->
                        nameOnCard = text

                    },
                    txt = stringResource(Res.string.name_on_card),
                    isPasswordField = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    onImeAction = { },
                    modifier = Modifier
                        .padding(horizontal = MARGIN_MEDIUM_2)
                        .fillMaxWidth()
                )

                //Delivery Address
                Text(
                    stringResource(Res.string.delivery_address),
                    fontSize = TEXT_REGULAR_3X,
                    color = TITLE_BLACK_COLOR,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = MARGIN_MEDIUM_2)
                )

                // Full Address
                FoodOrderingAppOutlineTxtField(

                    value = fullAddress,
                    onValueChange = { text ->
                        fullAddress = text

                    },
                    txt = stringResource(Res.string.full_address),
                    isPasswordField = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    onImeAction = { },
                    modifier = Modifier
                        .padding(horizontal = MARGIN_MEDIUM_2)
                        .fillMaxWidth(),
                    isMultiline = true,
                    minLines = 5
                )

                //SaveForFutureUse
                SaveForFutureUse(
                    checked = saveForFutureUse,
                    onCheckedChange = {
                        saveForFutureUse = !saveForFutureUse

                    })
            }

            //Log in Button Section
            FoodOrderingAppButton(
                onTapButton = {
                    onTapPlaceOrder(
                        cardNumber,
                        mm_yy,
                        cvv,
                        nameOnCard,
                        fullAddress
                    )
                },
                modifier =
                    Modifier
                        .padding(
                            horizontal = MARGIN_MEDIUM_2,
                            vertical = MARGIN_LARGE
                        )
                        .fillMaxWidth()
                        .height(48.dp).align(Alignment.BottomCenter),
                btnText = stringResource(Res.string.place_order),
                fontSize = TEXT_REGULAR_2X
            )
        }


    }

}

@Composable
private fun SaveForFutureUse(checked: Boolean, onCheckedChange: () -> Unit) {

    Row(
        modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2),
        verticalAlignment = Alignment.CenterVertically
    ) {

        //Save for future use
        Text(
            stringResource(Res.string.save_for_future_use),
            fontSize = TEXT_REGULAR_2X,
            color = TITLE_BLACK_COLOR,
            modifier = Modifier
                .weight(1f)
        )

        Switch(
            checked = checked,
            onCheckedChange = {
                onCheckedChange()
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color.Green,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = BUTTON_SWITCH_COLOR,
                uncheckedBorderColor = BUTTON_SWITCH_COLOR
            )
        )

    }
}

//@Preview
//@Composable
//fun CheckoutScreenPreview() {
//    CheckoutScreen(onTapBack = {}, onTapPlaceOrder = {})
//}