package org.ttm.foodorderingappcmp.features.restaurants.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.image_not_supported
import foodorderingappcmp.composeapp.generated.resources.order
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppButton
import org.ttm.foodorderingappcmp.common.ui.ShimmerBox
import org.ttm.foodorderingappcmp.core.MARGIN_40
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_SMALL
import org.ttm.foodorderingappcmp.core.OUTLINE_TXT_FIELD_TXT_COLOR
import org.ttm.foodorderingappcmp.core.RESTAURANT_IMAGE_HEIGHT
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR
import org.ttm.foodorderingappcmp.core.TEXT_SMALL
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.RestaurantVO

@Composable
fun RestaurantItemSection(
    restaurantVO: RestaurantVO,
    onTapOrder: (Long)-> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = MARGIN_MEDIUM_2).fillMaxWidth(),
        verticalArrangement =
            Arrangement.spacedBy(MARGIN_SMALL)
    ) {

        //Restaurant Image
        RestaurantImage(restaurantVO, onTapOrder = { restaurantId ->
            onTapOrder(restaurantId)
        })

        Row(
            verticalAlignment = Alignment.Bottom
        )
        {
            Column(modifier = Modifier
                .padding(horizontal = MARGIN_MEDIUM_2).weight(1f),
                verticalArrangement =
                    Arrangement.spacedBy(MARGIN_SMALL)) {

                //Restaurant Name
                RestaurantName(restaurantVO)

                //RestaurantCategory
                RestaurantCategories(restaurantVO)

                //Average Rating
                Text(
                    "${restaurantVO.averageRating} ⭐",
                    fontSize = TEXT_SMALL
                )




            }
            //Order
            FoodOrderingAppButton(
                onTapButton = {
                    onTapOrder(restaurantVO.id)
                },
                modifier = Modifier.height(MARGIN_40),
                btnText = stringResource(Res.string.order),
                fontSize = TEXT_SMALL
            )
        }



    }
}

@Composable
private fun RestaurantCategories(
    restaurantVO: RestaurantVO
) {
    val categories = restaurantVO.restaurantCategories

    categories?.let { category ->
        if (category.isEmpty()) return

        val categoryNames = category.map { it.name }

        Text(
            text = categoryNames.joinToString(", "),
            fontSize = TEXT_SMALL,
            color = OUTLINE_TXT_FIELD_TXT_COLOR
        )
    }

}


@Composable
private fun RestaurantName(
    restaurantVO: RestaurantVO
) {
    Text(
        restaurantVO.name,
        fontSize = TEXT_REGULAR,
        fontWeight = FontWeight.Bold,
        color = TITLE_BLACK_COLOR
    )
}

@Composable
private fun RestaurantImage(
    restaurantVO: RestaurantVO,
    onTapOrder: (Long) -> Unit
) {
    SubcomposeAsyncImage(
        restaurantVO.imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
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


        },
        modifier = Modifier
            .fillMaxWidth()
            .height(RESTAURANT_IMAGE_HEIGHT)
            .clip(shape = RoundedCornerShape(MARGIN_MEDIUM)).clickable{
                onTapOrder(restaurantVO.id)
            }
    )
}
