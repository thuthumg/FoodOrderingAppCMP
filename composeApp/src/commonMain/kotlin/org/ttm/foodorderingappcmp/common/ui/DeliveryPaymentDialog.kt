package org.ttm.foodorderingappcmp.common.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import org.ttm.foodorderingappcmp.core.ACTION_BAR_HEIGHT
import org.ttm.foodorderingappcmp.core.BUTTON_BG_COLOR
import org.ttm.foodorderingappcmp.core.MARGIN_CARD_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_LARGE
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_3X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressVO
import org.ttm.foodorderingappcmp.features.orders.data.vos.PaymentVO
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentListVO
import org.ttm.foodorderingappcmp.features.restaurants.detail.ui.ItemHeaderSection
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryPaymentDialog(
    deliveryAddressAndPaymentListVO: DeliveryAddressAndPaymentListVO?,
    onTapAddNew: () -> Unit,
    onTapConfirm: (DeliveryAddressVO, PaymentVO) -> Unit,
    onTapBack: () -> Unit
) {
//    val firstAddress = remember(deliveryAddressAndPaymentListVO?.deliveryAddresses) {
//        deliveryAddressAndPaymentListVO?.deliveryAddresses?.firstOrNull()?.streetAddress
//    }
//    var chosenDeliveryAddress by remember(deliveryAddressAndPaymentListVO?.deliveryAddresses) {
//        mutableStateOf(firstAddress)
//    }
//
//    val firstPayment = remember(deliveryAddressAndPaymentListVO?.paymentMethods) {
//        deliveryAddressAndPaymentListVO?.paymentMethods?.firstOrNull()?.cardNumber
//    }
//    var chosenPaymentMethod by remember(deliveryAddressAndPaymentListVO?.paymentMethods) {
//        mutableStateOf(firstPayment)
//    }

    var chosenDeliveryAddress by remember { mutableStateOf<String?>( "") }
    var chosenPaymentMethod by remember { mutableStateOf<String?>("") }

    var chosenDeliveryAddressVO: DeliveryAddressVO?  = null
    var chosenPaymentVO: PaymentVO? = null

    LaunchedEffect(deliveryAddressAndPaymentListVO?.deliveryAddresses) {
        chosenDeliveryAddress = deliveryAddressAndPaymentListVO?.deliveryAddresses?.firstOrNull()?.streetAddress
        chosenDeliveryAddressVO = deliveryAddressAndPaymentListVO?.deliveryAddresses?.firstOrNull()

    }

    LaunchedEffect(deliveryAddressAndPaymentListVO?.paymentMethods) {
        chosenPaymentMethod = deliveryAddressAndPaymentListVO?.paymentMethods?.firstOrNull()?.cardNumber
        chosenPaymentVO = deliveryAddressAndPaymentListVO?.paymentMethods?.firstOrNull()
    }


    BasicAlertDialog(
        onDismissRequest = onTapBack, // allow dismiss
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues()),
            color = Color.White,
            shape = RectangleShape
        ) {


            Box(Modifier.fillMaxSize()) {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = MARGIN_MEDIUM,
                        end = MARGIN_MEDIUM,
                        top = MARGIN_MEDIUM,
                        bottom = ACTION_BAR_HEIGHT + MARGIN_MEDIUM
                    )
                ) {

                    // Header row with title + close
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(MARGIN_MEDIUM),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = onTapBack,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = "Close",
                                    tint = Color.Black
                                )
                            }
                            Text(
                                text = "Select Delivery Address & Payment Method",
                                color = TITLE_BLACK_COLOR,
                                fontSize = TEXT_REGULAR_3X,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f).padding(start = MARGIN_MEDIUM)
                            )

                        }
                    }
                    item {
                        OutlinedButton(
                            onClick = onTapAddNew,
                            shape = RoundedCornerShape(MARGIN_MEDIUM),
                            border = BorderStroke(1.dp, BUTTON_BG_COLOR),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.Transparent,
                                contentColor = BUTTON_BG_COLOR
                            ),
                            modifier = Modifier
                                .padding(top = MARGIN_LARGE, start = MARGIN_MEDIUM,
                                    end = MARGIN_MEDIUM)
                                .height(58.dp)
                                .fillMaxWidth()
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    tint = Color.Red,
                                    contentDescription = null
                                )
                                Text("Add New", fontSize = TEXT_REGULAR_2X,
                                    textAlign = TextAlign.Center)
                            }


                        }

                    }
                    // Section 1 — Delivery addresses
                    item {
                        ItemHeaderSection(
                            headerName = "Previously used delivery address",
                            modifier = Modifier.padding(top = MARGIN_LARGE, bottom = MARGIN_MEDIUM)
                        )
                    }
                    deliveryAddressAndPaymentListVO?.deliveryAddresses?.let {
                        items(it.size) { index ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        chosenDeliveryAddressVO = it[index]
                                        chosenDeliveryAddress = it[index].streetAddress
                                    }
                                    .padding(vertical = 6.dp)
                            ) {
                                RadioButton(
                                    selected = it[index].streetAddress == chosenDeliveryAddress,
                                    onClick = {
                                        chosenDeliveryAddressVO = it[index]
                                        chosenDeliveryAddress = it[index].streetAddress
                                    }
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(it[index].streetAddress)
                            }
                        }
                    }


                    // Section 2 — Payment methods
                    item {
                        ItemHeaderSection(
                            headerName = "Previously used payment method",
                            modifier = Modifier.padding(top = MARGIN_LARGE, bottom = MARGIN_MEDIUM)
                        )
                    }
                    deliveryAddressAndPaymentListVO?.paymentMethods?.let {
                        items(it.size) { index ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        chosenPaymentVO = it[index]
                                        chosenPaymentMethod = it[index].cardNumber }
                                    .padding(vertical = 6.dp)
                            ) {
                                RadioButton(
                                    selected = it[index].cardNumber == chosenPaymentMethod,
                                    onClick = {
                                        chosenPaymentVO = it[index]
                                        chosenPaymentMethod = it[index].cardNumber }
                                )
                                Spacer(Modifier.width(8.dp))
                                Column {
                                    Text(it[index].nameOnCard)
                                    Text(it[index].cardNumber)
                                }
                            }
                        }
                    }

                }

                // Bottom action bar (unchanged)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(SCREEN_BG_COLOR)
                        .padding(
                            start = MARGIN_MEDIUM_2,
                            end = MARGIN_MEDIUM_2,
                            top = MARGIN_CARD_MEDIUM_2,
                            bottom = MARGIN_CARD_MEDIUM_2
                        )
                ) {
                    FoodOrderingAppButton(
                        onTapButton = {
                            chosenDeliveryAddressVO?.let { addressVO ->
                                chosenPaymentVO?.let { paymentVO ->
                                    onTapConfirm(addressVO, paymentVO)
                                }
                            } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),

                        btnText = "Confirm",
                        fontSize = TEXT_REGULAR_2X
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun DeliveryPaymentPreview(modifier: Modifier = Modifier) {
//    DeliveryPaymentDialog(
//        onTapAddNew1 = cartState.deliveryAddressAndPaymentListVO,
//        onTapAddNew = {},
//        onTapConfirm = {}
//    ) {}
//}