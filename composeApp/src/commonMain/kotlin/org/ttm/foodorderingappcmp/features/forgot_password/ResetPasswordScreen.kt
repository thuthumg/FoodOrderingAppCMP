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
import androidx.compose.runtime.LaunchedEffect
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
import foodorderingappcmp.composeapp.generated.resources.confirm_password
import foodorderingappcmp.composeapp.generated.resources.password
import foodorderingappcmp.composeapp.generated.resources.reset_password
import foodorderingappcmp.composeapp.generated.resources.reset_password_desc
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.ttm.foodorderingappcmp.common.ui.CommonAlertDialog
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppButton
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppOutlineTxtField
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppTopAppBar
import org.ttm.foodorderingappcmp.common.ui.LoadingDialog
import org.ttm.foodorderingappcmp.core.MARGIN_LARGE
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_3X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.features.forgot_password.actions.ForgotPasswordActions
import org.ttm.foodorderingappcmp.features.forgot_password.actions.ResetPasswordActions
import org.ttm.foodorderingappcmp.features.forgot_password.events.ResetPasswordEvents
import org.ttm.foodorderingappcmp.features.forgot_password.ui.state.ResetPasswordState
import org.ttm.foodorderingappcmp.features.forgot_password.ui.viewmodel.ResetPasswordViewModel

@Composable
fun ResetPasswordRoute(resetPasswordViewModel: ResetPasswordViewModel,
                        onTapBack: () -> Unit,
                        onNavigateToLogin: () -> Unit) {

    val resetPasswordState by resetPasswordViewModel.resetPasswordState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        resetPasswordViewModel.navigationSharedFlow.collectLatest { events ->
            when(events){
                is ResetPasswordEvents.NavigateToForgotPassword -> {
                    onTapBack()
                }
                is ResetPasswordEvents.NavigateToLogin -> {
                    onNavigateToLogin()
                }
            }

        }
    }



    ResetPasswordScreen(
        resetPasswordState = resetPasswordState,
        onAction = {
            resetPasswordViewModel.onAction(it)
        },
//        onTapBack = {
//            onTapBack()
//        },
//        onTapResetPassword = { password,confirmPassword ->
//            resetPasswordViewModel.forgotPassword(password,confirmPassword)
//        },
//        onDismissErrorAlertDialog = {
//            resetPasswordViewModel.onDismissErrorAlertDialog()
//        },
//        onDismissSuccessAlertDialog = {
//            resetPasswordViewModel.onDismissSuccessAlertDialog()
//        },
//        onNavigateToLogin = onNavigateToLogin
    )
}
@Composable
fun ResetPasswordScreen(
    resetPasswordState: ResetPasswordState,
    onAction: (ResetPasswordActions) -> Unit,
//    onTapBack: () -> Unit,
//    onTapResetPassword: (String, String) -> Unit,
//    onDismissErrorAlertDialog: ()-> Unit,
//    onDismissSuccessAlertDialog: () -> Unit,
//    onNavigateToLogin: () -> Unit
    ) {

   // var password by remember { mutableStateOf("") }
   // var confirmPassword by remember { mutableStateOf("") }



    /************* Loading State *********************/
    if (resetPasswordState.loading) {
        LoadingDialog(
            onDismissRequest = {}
        )
    }

    /************* API Call Error State *********************/
    if (resetPasswordState.message.isNotEmpty() && !resetPasswordState.showSuccessDialog) {

        CommonAlertDialog(
            title = "Error",
            message = resetPasswordState.message,
            onConfirm = {
                if(resetPasswordState.loginStatus){
                    onAction(ResetPasswordActions.OnUnauthorized())
                }
                else{
                    onAction(ResetPasswordActions.OnErrorDialogDismissed())
                }



            }
        )
    }else if (resetPasswordState.message.isNotEmpty() && resetPasswordState.showSuccessDialog) {
        CommonAlertDialog(
            title = "",
            message = resetPasswordState.message,
            onConfirm = {
               onAction(ResetPasswordActions.OnTapOK())

            }
        )
    }

    /************* Reset Password Screen *********************/

    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            FoodOrderingAppTopAppBar(
                stringResource(Res.string.reset_password),
                onTapBack = {
                    onAction(ResetPasswordActions.OnTapBack())
                })
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding).fillMaxSize(),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM_2)

            ) {

                //title section
                Text(
                    stringResource(Res.string.reset_password_desc),
                    fontSize = TEXT_REGULAR_3X,
                    color = TITLE_BLACK_COLOR,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        top = MARGIN_LARGE,
                        start = MARGIN_MEDIUM_2,
                        bottom = MARGIN_MEDIUM
                    )
                )


                //Password input section
                FoodOrderingAppOutlineTxtField(
                    value = resetPasswordState.password,
                    onValueChange = { text ->

                        onAction(ResetPasswordActions.OnPasswordChanged(text))
                    },
                    txt = stringResource(Res.string.password),
                    isPasswordField = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    onImeAction = { },
                    modifier = Modifier
                        .padding(horizontal = MARGIN_MEDIUM_2)
                        .fillMaxWidth()
                )

                //Confirm Password input section
                FoodOrderingAppOutlineTxtField(
                    value = resetPasswordState.confirmPassword,
                    onValueChange = { text ->
                        onAction(ResetPasswordActions.OnConfirmPasswordChanged(text))
                    },
                    txt = stringResource(Res.string.confirm_password),
                    isPasswordField = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    onImeAction = { },
                    modifier = Modifier
                        .padding(horizontal = MARGIN_MEDIUM_2)
                        .fillMaxWidth()
                )

            }

            //Reset Password Button Section
            FoodOrderingAppButton(
                onTapButton = {
                   // onTapResetPassword(password,confirmPassword)
                    onAction(ResetPasswordActions.OnTapResetPassword())
                },
                modifier =
                    Modifier
                        .padding(
                            horizontal = MARGIN_MEDIUM_2,
                            vertical = MARGIN_LARGE
                        )
                        .fillMaxWidth()
                        .height(48.dp).align(Alignment.BottomCenter),
                btnText = stringResource(Res.string.reset_password),
                fontSize = TEXT_REGULAR_2X
            )
        }


    }
}

//@Preview
//@Composable
//fun ResetPasswordScreenPreview() {
//    ResetPasswordScreen(onTapBack = {}, onTapResetPassword = {})
//}