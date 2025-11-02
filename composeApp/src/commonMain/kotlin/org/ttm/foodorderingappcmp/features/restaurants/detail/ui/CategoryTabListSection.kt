package org.ttm.foodorderingappcmp.features.restaurants.detail.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.core.CATEGORY_TAB_DIVIDER_COLOR
import org.ttm.foodorderingappcmp.core.CATEGORY_TAB_INDICATOR_COLOR
import org.ttm.foodorderingappcmp.core.CATEGORY_TAB_SELECTED_COLOR
import org.ttm.foodorderingappcmp.core.CATEGORY_TAB_SELECTED_INDICATOR_COLOR
import org.ttm.foodorderingappcmp.core.CATEGORY_TAB_UNSELECTED_COLOR
import org.ttm.foodorderingappcmp.core.MARGIN_LARGE
import org.ttm.foodorderingappcmp.core.MARGIN_XLARGE
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X

/**
 *
 * TabRow with LazyRow + small custom indicator + full width divider line.
 */
@Composable
fun CategoryTabListSection(
    tabs: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    selectedTextColor: Color = CATEGORY_TAB_SELECTED_COLOR,
    unselectedTextColor: Color = CATEGORY_TAB_UNSELECTED_COLOR,
    dividerColor: Color = CATEGORY_TAB_DIVIDER_COLOR,
    indicatorColor: Color = CATEGORY_TAB_INDICATOR_COLOR,
    selectedIndicatorColor: Color = CATEGORY_TAB_SELECTED_INDICATOR_COLOR,
    rowHorizontalPadding: Dp = MARGIN_LARGE,
    spaceBetweenItems: Dp = MARGIN_XLARGE,
    indicatorHeight: Dp = 4.dp,
    dividerThickness: Dp = 1.dp,
    horizontalScrollState: LazyListState,

    ) {

    Box(modifier.fillMaxWidth()) {
        // full-width divider
        HorizontalDivider(
            color = dividerColor,
            thickness = dividerThickness,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
        )

        LazyRow(
            state = horizontalScrollState,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = rowHorizontalPadding),
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenItems),
            verticalAlignment = Alignment.Bottom
        ) {

            //tabs.size
            itemsIndexed(tabs) { index, tabName ->

                val density = LocalDensity.current
               // var textWidthDp by remember(tabs[index]) { mutableStateOf(0.dp) } // per-item
                var textWidthDp by remember() { mutableStateOf(0.dp) }

                val selected = index == selectedIndex
                Column(
                    modifier = Modifier.clickable { onSelect(index) },
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        tabName,
                        fontSize = TEXT_REGULAR_2X,
                        fontWeight = if (selected) FontWeight.Black else FontWeight.SemiBold,
                        color = if (selected) selectedTextColor else unselectedTextColor,
                        onTextLayout = { result ->
                            textWidthDp = with(density) { result.size.width.toDp() }
                        }
                    )
                    Spacer(Modifier.height(10.dp)) // gap between text and bar

                    // indicator
                    Box(
                        Modifier
                            .width(textWidthDp)
                            .height(indicatorHeight)
                    ) {

                        HorizontalDivider(
                            color = if (selected) selectedIndicatorColor else indicatorColor,
                            thickness = indicatorHeight,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFCF8F8)
@Composable
private fun CategoryTabListSectionPreview() {
    var selected by remember { mutableStateOf(0) }
    val tabs = listOf("Featured", "Popular", "All","Featured", "Popular", "All","Featured", "Popular", "All")
   // var verticalScrollState = rememberLazyListState()
    MaterialTheme {
        Column(Modifier.fillMaxWidth().padding(top = 24.dp)) {
            CategoryTabListSection(
                tabs = tabs,
                selectedIndex = selected,
                onSelect = {
                    selected = it
//                    coroutineScope.launch {
//                        selected = it
//                        verticalScrollState.animateScrollToItem(selected)
//                    }

                },
                horizontalScrollState = rememberLazyListState()
            )
        }
    }
}
