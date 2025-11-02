package org.ttm.foodorderingappcmp.features.orders.cart.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.ttm.foodorderingappcmp.common.ui.QtyActionType
import org.ttm.foodorderingappcmp.common.ui.QuantityAdjustButton
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_3X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO


@Composable
fun QuantitySelector(
    itemQty: Int,
    onDecrease: (Int) -> Unit,
    onIncrease: (Int) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM)
    ) {

//        DecreaseItemQuantityButton(
//            itemQty = itemQty,
//            onDecrease = { it ->
//                onClickQtyAction(it)
//            }
//        )
        QuantityAdjustButton(
            modifier = Modifier.size(32.dp),
            type = QtyActionType.Decrease,
            onClick = {
               onDecrease(itemQty-1)

            }
        )

        ItemQuantitySection(
            quantity = itemQty
        )


        QuantityAdjustButton(
            modifier = Modifier.size(32.dp),
            type = QtyActionType.Increase,
            onClick = {
                onIncrease(itemQty+1)
            }
        )


//
//        IncreaseItemQuantityButton(
//            itemQty = itemQty,
//            onIncrease = {
//                it ->
//                onClickQtyAction(it)
//            }
//        )
    }
}

@Composable
private fun IncreaseItemQuantityButton(itemQty: Int, onIncrease: (Int) -> Unit) {
    QuantityAdjustButton(
        modifier = Modifier.size(32.dp),
        type = QtyActionType.Increase,
        onClick = {
            //Increase
            onIncrease(itemQty + 1)
        }
    )
}

@Composable
private fun DecreaseItemQuantityButton(itemQty: Int, onDecrease: (Int) -> Unit) {
    QuantityAdjustButton(
        modifier = Modifier.size(32.dp),
        type = QtyActionType.Decrease,
        onClick = {
            //Decrease
            if((itemQty -1) > 0)
            {
                onDecrease(itemQty - 1)
            }

        }
    )
}

@Composable
private fun ItemQuantitySection(quantity: Int) {
    Text(
        quantity.toString(),
        color = TITLE_BLACK_COLOR,
        fontSize = TEXT_REGULAR_3X,
        lineHeight = TEXT_REGULAR_3X,
        fontWeight = FontWeight.Bold
    )
}



