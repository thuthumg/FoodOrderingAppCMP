package org.ttm.foodorderingappcmp.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppButton
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_SMALL
import org.ttm.foodorderingappcmp.core.MARGIN_XLARGE
import org.ttm.foodorderingappcmp.core.OUTLINE_TXT_FIELD_TXT_COLOR
import org.ttm.foodorderingappcmp.core.RESTAURANT_IMAGE_HEIGHT
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR
import org.ttm.foodorderingappcmp.core.TEXT_SMALL
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR

@Composable
fun RestaurantItemSection(
    restaurantList: List<RestaurantItem>,
    index: Int,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = MARGIN_MEDIUM_2).fillMaxWidth(),
        verticalArrangement =
            Arrangement.spacedBy(MARGIN_SMALL)
    ) {

        //Restaurant Image
        Image(
            restaurantList[index].image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(RESTAURANT_IMAGE_HEIGHT).clip(shape = RoundedCornerShape(MARGIN_MEDIUM))
        )

        //Restaurant Name
        Text(
            restaurantList[index].name,
            fontSize = TEXT_REGULAR,
            fontWeight = FontWeight.Bold,
            color = TITLE_BLACK_COLOR
        )

        //Meal categories / Cuisine type
        Text(
            restaurantList[index].mealCategories,
            fontSize = TEXT_SMALL,
            color = OUTLINE_TXT_FIELD_TXT_COLOR
        )

        // Review
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                restaurantList[index].reviewData,
                fontSize = TEXT_SMALL,
                modifier = Modifier.weight(1.0f)
            )
            FoodOrderingAppButton(
                onTapButton = {},
                modifier = Modifier.height(MARGIN_XLARGE),
                btnText = "Order",
                fontSize = TEXT_SMALL
            )
        }
    }
}

data class RestaurantItem(
    val name: String,
    val image: Painter,
    val mealCategories: String,
    val reviewData: String
)

