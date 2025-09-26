package org.ttm.foodorderingappcmp.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.ic_add
import foodorderingappcmp.composeapp.generated.resources.ic_minus
import org.jetbrains.compose.resources.painterResource
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR

sealed class QtyActionType {
    object Increase : QtyActionType()
    object Decrease : QtyActionType()
    object AddToCart : QtyActionType()
}
@Composable
fun QuantityAdjustButton(
    type: QtyActionType,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val icon = when (type) {
        is QtyActionType.Increase -> painterResource(Res.drawable.ic_add)
        is QtyActionType.Decrease -> painterResource(Res.drawable.ic_minus)
        is QtyActionType.AddToCart -> painterResource(Res.drawable.ic_add)
    }

//val clickAction = when (type) {
//    is QtyActionType.Increase -> onIncrease
//    is QtyActionType.Decrease -> onDecrease
//    is QtyActionType.AddToCart -> onAddToCart
//}

    Box(
        modifier = modifier
            .background(color = Color.White, shape = CircleShape)
            .border(1.dp, color = Color(235, 41, 51), shape = CircleShape)
            .padding(6.dp)
            .clickable{
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = icon,
            contentDescription = type.toString(),
            tint = TITLE_BLACK_COLOR,
            modifier = Modifier.size(16.dp)
        )

    }

}
