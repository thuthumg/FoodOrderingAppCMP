package org.ttm.foodorderingappcmp.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.email
import foodorderingappcmp.composeapp.generated.resources.forgot_password
import foodorderingappcmp.composeapp.generated.resources.ic_info
import foodorderingappcmp.composeapp.generated.resources.log_in
import foodorderingappcmp.composeapp.generated.resources.password
import foodorderingappcmp.composeapp.generated.resources.welcome_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppButton
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppOutlineTxtField
import org.ttm.foodorderingappcmp.core.MARGIN_CARD_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_XLARGE
import org.ttm.foodorderingappcmp.core.OUTLINE_TXT_FIELD_TXT_COLOR
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TEXT_XXLARGE
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodOrderingAppLoginScreen(onTapLogin: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(red = 251, green = 247, blue = 247),
                ),
                title = {

                },
                navigationIcon = {

                },
                actions = {
                    //info icon section
                    Icon(
                        painterResource(Res.drawable.ic_info),
                        contentDescription = null,
                        modifier = Modifier.offset(
                            y = MARGIN_CARD_MEDIUM_2,
                            x = -MARGIN_CARD_MEDIUM_2
                        ).size(25.dp),
                        tint = TITLE_BLACK_COLOR
                    )
                }
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM_2)
        ) {

            //title section
            Text(
                stringResource(Res.string.welcome_back),
                fontSize = TEXT_XXLARGE,
                color = TITLE_BLACK_COLOR,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = MARGIN_XLARGE)
                    .align(Alignment.CenterHorizontally)
            )

            //Email input section
            FoodOrderingAppOutlineTxtField(
                value = email,
                onValueChange = { text ->
                    email = text
                },
                txt = stringResource(Res.string.email),
                isPasswordField = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                onImeAction = { })

            //Password input section
            FoodOrderingAppOutlineTxtField(

                value = password,
                onValueChange = { text ->
                    password = text

                },
                txt = stringResource(Res.string.password),
                isPasswordField = true,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
                onImeAction = { }
            )

            //forgot password section
            Text(
                stringResource(Res.string.forgot_password),
                fontSize = TEXT_REGULAR_2X,
                color = OUTLINE_TXT_FIELD_TXT_COLOR,
                modifier = Modifier.padding(top = MARGIN_CARD_MEDIUM_2)
                    .align(Alignment.CenterHorizontally)
            )


            //Log in Button Section
            FoodOrderingAppButton(
                onTapButton = {
                    onTapLogin()
                },
                modifier =
                    Modifier.padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_CARD_MEDIUM_2)
                        .fillMaxWidth().height(48.dp),
                btnText = stringResource(Res.string.log_in),
                fontSize = TEXT_REGULAR_2X
            )


        }
    }

}


@Preview
@Composable
fun FoodOrderingAppLoginScreenPreview() {
    FoodOrderingAppLoginScreen(onTapLogin = {})
}