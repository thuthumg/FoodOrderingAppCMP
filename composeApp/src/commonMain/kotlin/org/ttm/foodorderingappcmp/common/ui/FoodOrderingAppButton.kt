package org.ttm.foodorderingappcmp.common.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.order
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.core.BUTTON_BG_COLOR
import org.ttm.foodorderingappcmp.core.MARGIN_40
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.TEXT_SMALL

@Composable
fun FoodOrderingAppButton(
    onTapButton: () -> Unit,
    modifier: Modifier,
    btnText: String,
    fontSize: TextUnit,
    buttonContainerColor : Color = BUTTON_BG_COLOR,
    txtColor: Color = Color.White,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Button(
        onClick = {
            onTapButton()
        },
        shape = RoundedCornerShape(MARGIN_MEDIUM),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonContainerColor
        ),
        modifier = modifier
    ) {
        Text(
            btnText,
            fontSize = fontSize,
            textAlign = TextAlign.Center,
            color = txtColor,
            fontWeight = fontWeight


        )
    }
}


@Preview
@Composable
fun FoodOrderingAppButtonPreview(){
    FoodOrderingAppButton(
        onTapButton = {
           
        },
        modifier = Modifier.height(MARGIN_40),
        btnText = stringResource(Res.string.order),
        fontSize = TEXT_SMALL
    )

}