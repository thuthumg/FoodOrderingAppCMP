package org.ttm.foodorderingappcmp.common.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import org.ttm.foodorderingappcmp.core.BUTTON_BG_COLOR
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X

@Composable
fun FoodOrderingAppButton(
    onTapButton: () -> Unit,
    modifier: Modifier,
    btnText: String,
    fontSize: TextUnit
) {
    Button(
        onClick = {
            onTapButton()
        },
        shape = RoundedCornerShape(MARGIN_MEDIUM),
        colors = ButtonDefaults.buttonColors(
            containerColor = BUTTON_BG_COLOR
        ),
        modifier = modifier
    ) {
        Text(
            btnText,
            fontSize = fontSize,
            textAlign = TextAlign.Center
        )
    }
}
