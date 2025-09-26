package org.ttm.foodorderingappcmp.features.orders.cart.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.spicy_chicken_sandwich
import org.jetbrains.compose.resources.painterResource
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.TEXT_LARGE
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR


@Composable
fun CartItemRow(itemQty: Int,
                        onClickQtyAction: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_MEDIUM_2),
        horizontalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Image
        SelectedFoodItemImageSection(
            itemImage = painterResource(Res.drawable.spicy_chicken_sandwich)
        )

        //Selected Item Name and Quantity Adjustment
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),

            ) {

            SelectedFoodItemNameSection(
                itemName = "Spicy Chicken Sandwich"
            )

            QuantitySelector(
                itemQty = itemQty,
                onClickQtyAction = { it ->
                    onClickQtyAction(it)

                })

        }

        //Price
        SelectedItemPriceSection(
            itemPrice = "$12.99"
        )

    }
}


@Composable
private fun SelectedItemPriceSection(itemPrice: String) {
    Text(
        itemPrice,
        color = TITLE_BLACK_COLOR,
        fontSize = TEXT_REGULAR_2X,
    )
}


@Composable
private fun SelectedFoodItemNameSection(itemName: String) {
    Text(
        itemName,
        color = TITLE_BLACK_COLOR,
        fontSize = TEXT_REGULAR_2X,
        lineHeight = TEXT_LARGE
    )
}

@Composable
private fun SelectedFoodItemImageSection(
    itemImage: Painter,
) {
    Image(
        itemImage,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(85.dp).clip(
            RoundedCornerShape(MARGIN_MEDIUM)
        )
    )
}