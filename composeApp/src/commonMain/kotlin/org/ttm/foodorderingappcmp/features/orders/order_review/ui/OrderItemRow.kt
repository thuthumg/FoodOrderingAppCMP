package org.ttm.foodorderingappcmp.features.orders.order_review.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.features.orders.cart.ui.SelectedFoodItemImageSection
import org.ttm.foodorderingappcmp.features.orders.cart.ui.SelectedFoodItemNameSection
import org.ttm.foodorderingappcmp.features.orders.cart.ui.SelectedItemPriceSection
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

@Composable
fun OrderItemRow(foodItemVO : FoodItemVO) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_MEDIUM_2),
        horizontalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM_2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Image
        SelectedFoodItemImageSection(
            itemImage = foodItemVO.imageUrl
        )

        //Selected Item Name and Quantity Adjustment
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),

            ) {

            SelectedFoodItemNameSection(
                itemName = foodItemVO.name
            )

            Text(
                "${foodItemVO.quantity}x",
                color = TITLE_BLACK_COLOR,
                fontSize = TEXT_REGULAR_2X,
            )

        }

        //Price
        SelectedItemPriceSection(
            itemPrice = "$${foodItemVO.price}"
        )

    }
}

