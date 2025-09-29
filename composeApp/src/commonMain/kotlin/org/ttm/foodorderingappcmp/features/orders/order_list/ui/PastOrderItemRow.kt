package org.ttm.foodorderingappcmp.features.orders.order_list.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.ic_forward
import foodorderingappcmp.composeapp.generated.resources.past_order_pic
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.OUTLINE_TXT_FIELD_TXT_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_LARGE
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TEXT_SMALL
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR

@Composable
fun PastOrderItemRow(onTapItem:() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_MEDIUM_2)
            .clickable{
                onTapItem()
            },
        horizontalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Image
        Image(
            painterResource(Res.drawable.past_order_pic),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(70.dp).clip(
                CircleShape
            )
        )
        //Selected Item Name and Quantity Adjustment
        Column(
            modifier = Modifier
                .weight(1f),
           // verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),

            ) {

            Text(
                "Taco Fiesta",
                color = TITLE_BLACK_COLOR,
                fontSize = TEXT_REGULAR_2X,
                lineHeight = TEXT_LARGE
            )

            Text(
                "Tacos, Burritos, Quesadillas",
                color = Color(135, 99, 99),
                fontSize = TEXT_SMALL,
            )
            Text(
                "Wednesday Nov 9 · 3 items",
                color = Color(135, 99, 99),
                fontSize = TEXT_SMALL,
            )


        }

        Image(
            painterResource(Res.drawable.ic_forward),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(28.dp).clip(
                CircleShape
            )
        )

    }
}

@Preview
@Composable
fun PastOrderItemRowPreview() {
    PastOrderItemRow(onTapItem = {})
}