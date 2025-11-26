package org.ttm.foodorderingappcmp.auth.ui

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.dont_have_an_account
import foodorderingappcmp.composeapp.generated.resources.email
import foodorderingappcmp.composeapp.generated.resources.forgot_password
import foodorderingappcmp.composeapp.generated.resources.ic_info
import foodorderingappcmp.composeapp.generated.resources.log_in
import foodorderingappcmp.composeapp.generated.resources.password
import foodorderingappcmp.composeapp.generated.resources.sign_up
import foodorderingappcmp.composeapp.generated.resources.welcome_back
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.ttm.foodorderingappcmp.auth.actions.LoginActions
import org.ttm.foodorderingappcmp.auth.events.LoginEvents
import org.ttm.foodorderingappcmp.auth.ui.state.LoginState
import org.ttm.foodorderingappcmp.auth.ui.viewmodel.LoginViewModel
import org.ttm.foodorderingappcmp.common.ui.CommonAlertDialog
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppButton
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppOutlineTxtField
import org.ttm.foodorderingappcmp.common.ui.LoadingDialog
import org.ttm.foodorderingappcmp.core.MARGIN_CARD_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_XLARGE
import org.ttm.foodorderingappcmp.core.OUTLINE_TXT_FIELD_TXT_COLOR
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TEXT_XXLARGE
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.core.utils.apiToken


@Composable
fun FoodOrderingAppLoginScreenRoute(loginViewModel: LoginViewModel,
                                    onNavigateToHome: () -> Unit,
                                    onTapSignUp: ()-> Unit,
                                    onTapForgotPassword: () -> Unit) {


    val state by loginViewModel.state.collectAsStateWithLifecycle()


    LaunchedEffect(Unit){
        loginViewModel.navigationSharedFlow.collectLatest { event ->
            when(event){
                is LoginEvents.NavigateToHome ->{
                    onNavigateToHome()
                }
                is LoginEvents.NavigateToSignUp -> {
                    onTapSignUp()
                }
                is LoginEvents.NavigateToForgotPassword -> {
                    onTapForgotPassword()
                }
            }

        }

    }

    FoodOrderingAppLoginScreen(
        state = state,
        onAction = {
            loginViewModel.onAction(it)
        }
    )


}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodOrderingAppLoginScreen(
    state : LoginState,
    onAction: (LoginActions) -> Unit) {

    val focusManager = LocalFocusManager.current
    val passwordFocusRequester = remember { FocusRequester() }


    /************ Loading *****************/
    if (state.loading) {
        LoadingDialog(
            onDismissRequest = {}
        )
    }

    /************ Login Fail *****************/

    if(state.message.isNotBlank()){
        apiToken = ""
        CommonAlertDialog(
            title = "Error",
            message = state.message,
            onConfirm = {
                onAction(LoginActions.OnErrorDialogDismissed())
            }
        )
    }
    /******************** Login Screen *********************************/
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
        }
        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding).fillMaxSize(),
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
                value = state.email,
                onValueChange = { text ->
                    onAction(LoginActions.OnEmailChanged(text))
                },
                txt = stringResource(Res.string.email),
                isPasswordField = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                onImeAction = {
                    passwordFocusRequester.requestFocus()
                },
                modifier = Modifier
                    .padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_MEDIUM_2)
                    .fillMaxWidth())

            //Password input section
            FoodOrderingAppOutlineTxtField(

                value = state.password,
                onValueChange = { text ->
                    onAction(LoginActions.OnPasswordChanged(text))

                },
                txt = stringResource(Res.string.password),
                isPasswordField = true,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
                onImeAction = {
                    focusManager.clearFocus()
                    onAction(LoginActions.OnTapLoginAction())

                },
                modifier = Modifier
                    .padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_MEDIUM)
                    .fillMaxWidth()
            )

            //forgot password section
            Text(
                stringResource(Res.string.forgot_password),
                fontSize = TEXT_REGULAR_2X,
                color = OUTLINE_TXT_FIELD_TXT_COLOR,
                modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_MEDIUM)
                    .align(Alignment.End).clickable{
                        onAction(LoginActions.OnTapForgotPasswordAction())
                    }
            )


            //Log in Button Section
            FoodOrderingAppButton(
                onTapButton = {
                    onAction(LoginActions.OnTapLoginAction())
                },
                modifier =
                    Modifier.padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_CARD_MEDIUM_2)
                        .fillMaxWidth().height(48.dp),
                btnText = stringResource(Res.string.log_in),
                fontSize = TEXT_REGULAR_2X
            )


            //Don't have an account section
            Text(
                stringResource(Res.string.dont_have_an_account),
                fontSize = TEXT_REGULAR_2X,
                color = TITLE_BLACK_COLOR,
                modifier = Modifier.padding(top = MARGIN_MEDIUM_2)
                    .align(Alignment.CenterHorizontally)
            )

            //Sign Up
            Text(
                stringResource(Res.string.sign_up),
                fontSize = TEXT_REGULAR_2X,
                color = OUTLINE_TXT_FIELD_TXT_COLOR,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = MARGIN_MEDIUM)
                    .align(Alignment.CenterHorizontally)
                    .clickable{
                        onAction(LoginActions.OnTapSignUpAction())
                    }
            )


        }
    }

}

//
//@Preview
//@Composable
//fun FoodOrderingAppLoginScreenPreview() {
//    FoodOrderingAppLoginScreen(onTapLogin = {}, onTapSignUp = {}, onTapForgotPassword = {})
//}