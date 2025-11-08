package org.ttm.foodorderingappcmp.features.restaurants.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.image_not_supported
import org.jetbrains.compose.resources.painterResource
import org.ttm.foodorderingappcmp.common.ui.QtyActionType
import org.ttm.foodorderingappcmp.common.ui.QuantityAdjustButton
import org.ttm.foodorderingappcmp.common.ui.ShimmerBox
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_3
import org.ttm.foodorderingappcmp.core.MARGIN_SMALL
import org.ttm.foodorderingappcmp.core.OUTLINE_TXT_FIELD_TXT_COLOR
import org.ttm.foodorderingappcmp.core.PRICE_BUTTON_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TEXT_SMALL
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

@Composable
fun ItemDetailSection(
    foodItem: FoodItemVO,
    onTapAddToCart: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_MEDIUM_2).background(
                color = Color.Transparent
            ),
        horizontalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ItemDescriptionSection(foodItem)

        ItemImageSection(
            foodItemVO = foodItem,
            onTapAddToCart = { it ->
            onTapAddToCart(it)
        })
    }
}

@Composable
private fun ItemImageSection(
    foodItemVO: FoodItemVO,
    onTapAddToCart: (Boolean) -> Unit
) {

    Box(
        modifier = Modifier
            .size(width = 100.dp, height = 100.dp)
            .clip(RoundedCornerShape(MARGIN_MEDIUM))
    ) {
        //Item Image
        FoodImageSection(foodItemVO.imageUrl)

        //Add To Cart
        AddToCartSection(
            foodItemVO = foodItemVO,
            onTapAddToCart = { it ->
                onTapAddToCart(it)
            }
        )

    }
}

@Composable
private fun RowScope.ItemDescriptionSection(foodItem: FoodItemVO) {
    Column(
        modifier = Modifier
            .weight(1f),
        verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),
        ) {

       // MenuNameSection()
        FoodNameSection(foodItem.name)
        FoodDescriptionSection(foodItem.description)
        PriceSection(foodItem.price)
    }
}


@Composable
private fun BoxScope.AddToCartSection(
    foodItemVO: FoodItemVO,
    onTapAddToCart: (Boolean) -> Unit) {

    if (foodItemVO.quantity == 0) {
        QuantityAdjustButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = -MARGIN_SMALL, y = -MARGIN_SMALL)
                .size(25.dp),
            type = QtyActionType.AddToCart,
            onClick = {
                //add to cart
                onTapAddToCart(true)
            })
    } else {
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = -MARGIN_SMALL, y = -MARGIN_SMALL)
                .size(25.dp)
                .background(color = Color.White, shape = CircleShape)
                .border(1.dp, color = Color(235, 41, 51), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {

            Text(
                foodItemVO.quantity.toString(),
                fontSize = TEXT_SMALL,
                fontWeight = FontWeight.Bold
            )

        }
    }




}

@Composable
private fun FoodImageSection(imgUrl: String) {
    SubcomposeAsyncImage(
        imgUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        loading = {
            // CircularProgressIndicator(modifier = Modifier.size(30.dp))
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
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun PriceSection(price: Double) {
    Box(
        modifier = Modifier.height(32.dp)
            .width(80.dp)
            .background(
                color = PRICE_BUTTON_BG_COLOR,
                shape = RoundedCornerShape(MARGIN_MEDIUM_3),
            )
    ) {
        Text(
            "$${price}",
            fontSize = TEXT_SMALL,
            color = TITLE_BLACK_COLOR,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().align(Alignment.Center)
        )
    }
}

@Composable
private fun MenuNameSection() {
    Text(
        "Popular",
        color = OUTLINE_TXT_FIELD_TXT_COLOR,
        fontSize = TEXT_REGULAR,
    )
}

@Composable
private fun FoodDescriptionSection(description: String) {
    Text(
        description,
        color = OUTLINE_TXT_FIELD_TXT_COLOR,
        fontSize = TEXT_REGULAR,
    )
}

@Composable
private fun FoodNameSection(name: String) {
    Text(
        name,
        color = TITLE_BLACK_COLOR,
        fontSize = TEXT_REGULAR_2X,
        fontWeight = FontWeight.Bold
    )
}


//@Preview
//@Composable
//fun MenuBodySectionPreview(modifier: Modifier = Modifier) {
//    ItemDetailSection(
//        foodItem = foodItem,
//        showAddToCart = false
//    ) {}
//}