package org.ttm.foodorderingappcmp.features.forgot_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.continue_txt
import foodorderingappcmp.composeapp.generated.resources.email
import foodorderingappcmp.composeapp.generated.resources.forgot_password_desc
import foodorderingappcmp.composeapp.generated.resources.forgot_password_title
import foodorderingappcmp.composeapp.generated.resources.forgot_password_txt
import org.jetbrains.compose.resources.stringResource
import org.ttm.foodorderingappcmp.common.ui.CommonAlertDialog
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppButton
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppOutlineTxtField
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppTopAppBar
import org.ttm.foodorderingappcmp.common.ui.LoadingDialog
import org.ttm.foodorderingappcmp.core.MARGIN_LARGE
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_3X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.core.utils.apiToken
import org.ttm.foodorderingappcmp.features.forgot_password.ui.state.ForgotPasswordState
import org.ttm.foodorderingappcmp.features.forgot_password.ui.viewmodel.ForgotPasswordViewModel

@Composable
fun ForgotPasswordRoute(forgotPasswordViewModel: ForgotPasswordViewModel,
                        onTapBack: () -> Unit,
                        onNavigateToResetPassword: (String) -> Unit) {

    val forgotPasswordState by forgotPasswordViewModel.forgotPasswordState.collectAsStateWithLifecycle()

    ForgotPasswordScreen(
        forgotPasswordState = forgotPasswordState,
        onTapBack = {
            onTapBack()
        },
        onTapContinue = { email ->
           forgotPasswordViewModel.checkEmail(email)
        },
        onDismissErrorAlertDialog = {
            forgotPasswordViewModel.onDismissErrorAlertDialog()
        },
        onNavigateToResetPassword = { email->
            onNavigateToResetPassword(email)
            
        }
    )
}
@Composable
fun ForgotPasswordScreen(forgotPasswordState: ForgotPasswordState,
                         onTapBack: () -> Unit,
                         onTapContinue: (String)-> Unit,
                         onDismissErrorAlertDialog: () -> Unit,
                         onNavigateToResetPassword: (String) -> Unit) {
    var email by remember{ mutableStateOf("") }

    /************* Loading State *********************/
    if (forgotPasswordState.loading) {
        LoadingDialog(
            onDismissRequest = {}
        )
    }

    /************* API Call Error State *********************/
    if (forgotPasswordState.message.isNotBlank() && (forgotPasswordState.errorDialogShowStatus)) {

        CommonAlertDialog(
            title = "Error",
            message = forgotPasswordState.message,
            onConfirm = {
                onDismissErrorAlertDialog()

            }
        )
    }
    /************* API Call Success State *********************/
    forgotPasswordState.checkEmailResponse?.let {
        apiToken = it.resetPasswordToken
        onNavigateToResetPassword(it.user.email)
    }

    /************* Forgot Password Screen *********************/
    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            FoodOrderingAppTopAppBar(
                stringResource(Res.string.forgot_password_title),
                onTapBack = {
                    onTapBack()
                })
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding).fillMaxSize(),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM_2),
                modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2)
            ) {

                //title section
                Text(
                    stringResource(Res.string.forgot_password_txt),
                    fontSize = TEXT_REGULAR_3X,
                    color = TITLE_BLACK_COLOR,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = MARGIN_MEDIUM_2)
                )

                //title section
                Text(
                    stringResource(Res.string.forgot_password_desc),
                    fontSize = TEXT_REGULAR_2X,
                    color = TITLE_BLACK_COLOR,
                    modifier = Modifier
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
                    imeAction = ImeAction.Done,
                    onImeAction = { },
                    modifier = Modifier
                        .fillMaxWidth()
                )

            }

            //Continue Button Section
            FoodOrderingAppButton(
                onTapButton = {
                    onTapContinue(email)
                },
                modifier =
                    Modifier
                        .padding(
                            horizontal = MARGIN_MEDIUM_2,
                            vertical = MARGIN_LARGE
                        )
                        .fillMaxWidth()
                        .height(48.dp).align(Alignment.BottomCenter),
                btnText = stringResource(Res.string.continue_txt),
                fontSize = TEXT_REGULAR_2X
            )
        }


    }
}

//@Preview
//@Composable
//fun ForgotPasswordScreenPreview() {
//    ForgotPasswordScreen(onTapBack = {}, onTapContinue = {})
//}