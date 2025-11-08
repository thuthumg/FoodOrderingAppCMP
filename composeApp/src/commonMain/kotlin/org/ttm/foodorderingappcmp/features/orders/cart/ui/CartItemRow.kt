package org.ttm.foodorderingappcmp.features.orders.cart.ui

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.TEXT_LARGE
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO


@Composable
fun CartItemRow(
    foodItemVO: FoodItemVO,
    onIncrease:(FoodItemVO) -> Unit,
    onDecrease : (FoodItemVO) -> Unit){
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

            QuantitySelector(
                itemQty = foodItemVO.quantity ?: 0,
                onIncrease = { itemQty ->
                    onIncrease(foodItemVO.copy(
                        quantity = itemQty
                    ))
                },
                onDecrease = { itemQty ->
                    onDecrease(foodItemVO.copy(
                        quantity = itemQty
                    ))
                }
            )

        }

        //Price
        SelectedItemPriceSection(
            itemPrice = "$${foodItemVO.getItemPrice()}"
        )

    }
}


@Composable
fun SelectedItemPriceSection(itemPrice: String) {
    Text(
        itemPrice,
        color = TITLE_BLACK_COLOR,
        fontSize = TEXT_REGULAR_2X,
    )
}


@Composable
fun SelectedFoodItemNameSection(itemName: String) {
    Text(
        itemName,
        color = TITLE_BLACK_COLOR,
        fontSize = TEXT_REGULAR_2X,
        lineHeight = TEXT_LARGE
    )
}

@Composable
fun SelectedFoodItemImageSection(
    itemImage: String,
) {
    SubcomposeAsyncImage(
        itemImage,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(56.dp).clip(
            RoundedCornerShape(MARGIN_MEDIUM)
        )
    )
}