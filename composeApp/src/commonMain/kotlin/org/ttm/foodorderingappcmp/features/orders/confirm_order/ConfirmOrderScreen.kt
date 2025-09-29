package org.ttm.foodorderingappcmp.features.orders.confirm_order

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.confirm
import foodorderingappcmp.composeapp.generated.resources.order_confirm_desc
import foodorderingappcmp.composeapp.generated.resources.order_confirm_pic
import foodorderingappcmp.composeapp.generated.resources.order_confirmed
import foodorderingappcmp.composeapp.generated.resources.your_order_is_confirmed
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppButton
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppTopAppBar
import org.ttm.foodorderingappcmp.core.MARGIN_LARGE
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_3X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR

@Composable
fun ConfirmOrderScreen(onTapBack: () -> Unit, onTapConfirmOrder: () -> Unit) {
    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            FoodOrderingAppTopAppBar(
                stringResource(Res.string.order_confirmed),
                onTapBack = {
                    onTapBack()
                })
        }
    ) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {

            Column(
                modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2),

                ) {

                Image(
                    painterResource(Res.drawable.order_confirm_pic),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(200.dp).clip(
                        RoundedCornerShape(MARGIN_MEDIUM)
                    )
                )

                Text(
                    stringResource(Res.string.your_order_is_confirmed),
                    fontSize = TEXT_REGULAR_3X,
                    color = TITLE_BLACK_COLOR,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        top = MARGIN_LARGE
                    ).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    stringResource(Res.string.order_confirm_desc),
                    fontSize = TEXT_REGULAR_2X,
                    color = TITLE_BLACK_COLOR,
                    modifier = Modifier.padding(
                        top = MARGIN_MEDIUM_2
                    ).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                FoodOrderingAppButton(
                    onTapButton = {
                        onTapConfirmOrder()
                    },
                    modifier =
                        Modifier
                            .padding(

                                vertical = MARGIN_MEDIUM_2
                            )
                            .fillMaxWidth()

                            .height(48.dp),
                    btnText = stringResource(Res.string.confirm),
                    fontSize = TEXT_REGULAR_2X
                )
            }


        }

    }
}

@Preview
@Composable
fun ConfirmOrderScreenPreview() {
    ConfirmOrderScreen(onTapConfirmOrder = {}, onTapBack = {})
}