package org.ttm.foodorderingappcmp.features.restaurants.detail.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_3X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR


@Composable
fun ItemHeaderSection(modifier: Modifier) {
    Text(
        "Featured",
        color = TITLE_BLACK_COLOR,
        fontSize = TEXT_REGULAR_3X,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_MEDIUM)
    )
}


