package org.ttm.foodorderingappcmp.features.orders.order_list.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.ic_forward
import foodorderingappcmp.composeapp.generated.resources.image_not_supported
import org.jetbrains.compose.resources.painterResource
import org.ttm.foodorderingappcmp.common.ui.ShimmerBox
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.TEXT_LARGE
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TEXT_SMALL
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.features.orders.data.vos.OrderItemVO

@Composable
fun SubmittedOrderItemRow(onTapItem: () -> Unit, orderItemVO: OrderItemVO) {
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
        SubcomposeAsyncImage(
            orderItemVO.foodItems.firstOrNull()?.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(70.dp).clip(
                CircleShape
            ),
            loading = {
                ShimmerBox(Modifier.fillMaxSize())
            },
            error = {

                Box(
                    modifier = Modifier
                        .background(Color.Gray.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.image_not_supported),
                        contentDescription = "Error loading image",
                        modifier = Modifier.size(30.dp),
                        tint = Color.Black
                    )
                }


            }
        )


        Column(
            modifier = Modifier
                .weight(1f),
           // verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),

            ) {

            Text(
                orderItemVO.foodItems.firstOrNull()?.name ?: "",
                color = TITLE_BLACK_COLOR,
                fontSize = TEXT_REGULAR_2X,
                lineHeight = TEXT_LARGE
            )

            Text(
                orderItemVO.foodItems
                    .drop(1) // removes the first item
                    .joinToString(",") { it.name },
                color = Color(135, 99, 99),
                fontSize = TEXT_SMALL,
            )
            Text(
                "${orderItemVO.orderDisplayDate()} · ${orderItemVO.foodItems.size} items",
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

//@Preview
//@Composable
//fun PastOrderItemRowPreview() {
//    PastOrderItemRow(onTapItem = {},)
//}