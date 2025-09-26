package org.ttm.foodorderingappcmp.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_3X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodOrderingAppTopAppBar(title: String,onTapBack: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = TITLE_BLACK_COLOR,
                fontSize = TEXT_REGULAR_3X,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            Icon(
                Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = null,
                tint = TITLE_BLACK_COLOR,
                modifier = Modifier.clickable{
                    onTapBack()
                }
            )
        }
    )
}

@Preview
@Composable
fun FoodOrderingAppTopAppBarPreview(modifier: Modifier = Modifier) {
    FoodOrderingAppTopAppBar("", onTapBack = {})
}