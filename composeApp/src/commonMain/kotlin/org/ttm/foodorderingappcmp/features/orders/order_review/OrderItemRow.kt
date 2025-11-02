package org.ttm.foodorderingappcmp.features.orders.order_review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.spicy_chicken_sandwich
import org.jetbrains.compose.resources.painterResource
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.features.orders.cart.ui.SelectedFoodItemImageSection
import org.ttm.foodorderingappcmp.features.orders.cart.ui.SelectedFoodItemNameSection
import org.ttm.foodorderingappcmp.features.orders.cart.ui.SelectedItemPriceSection

@Composable
fun OrderItemRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_MEDIUM_2),
        horizontalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM_2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Image
//        SelectedFoodItemImageSection(
//            itemImage = painterResource(Res.drawable.spicy_chicken_sandwich)
//        )

        //Selected Item Name and Quantity Adjustment
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),

            ) {

            SelectedFoodItemNameSection(
                itemName = "Spicy Chicken Sandwich"
            )

            Text(
                "1x",
                color = TITLE_BLACK_COLOR,
                fontSize = TEXT_REGULAR_2X,
            )

        }

        //Price
        SelectedItemPriceSection(
            itemPrice = "$12.99"
        )

    }
}

