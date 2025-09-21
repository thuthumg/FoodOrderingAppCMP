package org.ttm.foodorderingappcmp.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.hide
import foodorderingappcmp.composeapp.generated.resources.show
import org.jetbrains.compose.resources.painterResource
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.OUTLINE_TXT_FIELD_BG_COLOR
import org.ttm.foodorderingappcmp.core.OUTLINE_TXT_FIELD_TXT_COLOR
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR


@Composable
fun FoodOrderingAppOutlineTxtField(
    value: String,
    onValueChange: (String) -> Unit,
    txt: String,
    isPasswordField: Boolean,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = if (isPasswordField) KeyboardType.Password else KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {},
) {
    var isPasswordShown by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = txt,
                color = OUTLINE_TXT_FIELD_TXT_COLOR
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = OUTLINE_TXT_FIELD_BG_COLOR,
            unfocusedContainerColor = OUTLINE_TXT_FIELD_BG_COLOR,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedTextColor = OUTLINE_TXT_FIELD_TXT_COLOR,
            unfocusedTextColor = OUTLINE_TXT_FIELD_TXT_COLOR,
        ),
        shape = RoundedCornerShape(MARGIN_MEDIUM),
        modifier = modifier
            .padding(horizontal = MARGIN_MEDIUM_2)
            .fillMaxWidth(),
        visualTransformation = if (isPasswordField && !isPasswordShown) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        suffix = if (isPasswordField) {
            {
                Icon(
                    painter = if (isPasswordShown) painterResource(Res.drawable.hide)
                    else painterResource(Res.drawable.show),
                    contentDescription = null,
                    tint = TITLE_BLACK_COLOR,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { isPasswordShown = !isPasswordShown }
                )
            }
        } else null,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { onImeAction() },
            onGo = { onImeAction() },
            onSearch = { onImeAction() },
            onSend = { onImeAction() },
            onNext = { onImeAction() },
            onPrevious = { onImeAction() }
        ),
        singleLine = true
    )
}