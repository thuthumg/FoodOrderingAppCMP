package org.ttm.foodorderingappcmp.features.orders.cart.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.cart
import foodorderingappcmp.composeapp.generated.resources.place_order
import foodorderingappcmp.composeapp.generated.resources.total
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppButton
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppTopAppBar
import org.ttm.foodorderingappcmp.core.MARGIN_CARD_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_LARGE
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR

@Composable
fun CartScreen(
    onTapBack: () -> Unit,
    onTapPlaceOrder: () -> Unit,
) {

    var qty by remember { mutableStateOf(1) }

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

        LazyColumn(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {

            //Selected Food Item
            items(3) {
                CartItemRow(
                    itemQty = qty,
                    onClickQtyAction = { it ->
                        qty = it

                    })

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
                TotalPriceSection(
                    totalPrice = "$38.97"
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


@Preview
@Composable
fun CartScreenPreview() {
    CartScreen(onTapBack = {}, onTapPlaceOrder = {})
}