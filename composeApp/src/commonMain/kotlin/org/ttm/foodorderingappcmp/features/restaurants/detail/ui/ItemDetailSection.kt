package org.ttm.foodorderingappcmp.features.restaurants.detail.ui

import androidx.compose.foundation.Image
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
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.spicy_chicken_sandwich
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.common.ui.QtyActionType
import org.ttm.foodorderingappcmp.common.ui.QuantityAdjustButton
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

@Composable
fun ItemDetailSection(showAddToCart: Boolean,onTapAddToCart: (Boolean) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_MEDIUM_2).background(
                color = Color.Transparent
            ),
        horizontalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ItemDescriptionSection()

        ItemImageSection(
            showAddToCart = showAddToCart,
            onTapAddToCart = { it ->
            onTapAddToCart(it)
        })
    }
}

@Composable
private fun ItemImageSection(showAddToCart: Boolean, onTapAddToCart: (Boolean) -> Unit) {

    Box(
        modifier = Modifier
            .size(width = 100.dp, height = 100.dp)
            .clip(RoundedCornerShape(MARGIN_MEDIUM))
    ) {
        //Item Image
        FoodImageSection()

        //Add To Cart
        AddToCartSection(
            showAddToCart = showAddToCart,
            onTapAddToCart = { it ->
                onTapAddToCart(it)
            }
        )

    }
}

@Composable
private fun RowScope.ItemDescriptionSection() {
    Column(
        modifier = Modifier
            .weight(1f),
        verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM),
        ) {

        MenuNameSection()
        FoodNameSection()
        FoodDescriptionSection()
        PriceSection()
    }
}


@Composable
private fun BoxScope.AddToCartSection(
    showAddToCart: Boolean,
    onTapAddToCart: (Boolean) -> Unit) {

    if (!showAddToCart) {
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
                "1",
                fontSize = TEXT_SMALL,
                fontWeight = FontWeight.Bold
            )

        }
    }




}

@Composable
private fun FoodImageSection() {
    Image(
        painterResource(Res.drawable.spicy_chicken_sandwich),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun PriceSection() {
    Box(
        modifier = Modifier.height(32.dp)
            .width(80.dp)
            .background(
                color = PRICE_BUTTON_BG_COLOR,
                shape = RoundedCornerShape(MARGIN_MEDIUM_3),
            )
    ) {
        Text(
            "$9.99",
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
private fun FoodDescriptionSection() {
    Text(
        "Crispy chicken, spicy mayo, lettuce,tomato",
        color = OUTLINE_TXT_FIELD_TXT_COLOR,
        fontSize = TEXT_REGULAR,
    )
}

@Composable
private fun FoodNameSection() {
    Text(
        "Spicy Chicken Sandwich",
        color = TITLE_BLACK_COLOR,
        fontSize = TEXT_REGULAR_2X,
        fontWeight = FontWeight.Bold
    )
}


@Preview
@Composable
fun MenuBodySectionPreview(modifier: Modifier = Modifier) {
    ItemDetailSection(
        showAddToCart = false,
        onTapAddToCart = {}
    )
}