package org.ttm.foodorderingappcmp.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.create_account
import foodorderingappcmp.composeapp.generated.resources.email
import foodorderingappcmp.composeapp.generated.resources.ic_info
import foodorderingappcmp.composeapp.generated.resources.name
import foodorderingappcmp.composeapp.generated.resources.password
import foodorderingappcmp.composeapp.generated.resources.terms_of_service
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.auth.ui.viewmodel.LoginRegisterViewModel
import org.ttm.foodorderingappcmp.common.ui.ErrorAlertDialog
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppButton
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppOutlineTxtField
import org.ttm.foodorderingappcmp.common.ui.LoadingDialog
import org.ttm.foodorderingappcmp.core.MARGIN_CARD_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_XLARGE
import org.ttm.foodorderingappcmp.core.OUTLINE_TXT_FIELD_TXT_COLOR
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TEXT_XXLARGE
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.core.utils.apiToken


@Composable
fun FoodOrderingAppRegisterRoute(viewModel: LoginRegisterViewModel,
                                 onNavigateHome: ()-> Unit) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    if(state.successStatus){
        state.loginRegisterVO?.let {
            apiToken = it.accessToken
            onNavigateHome()
        }
    }
    else{
        if (state.message.isNotBlank() && !(state.dismissStatus)) {
            ErrorAlertDialog(
                showDialog = true,
                title = "Error",
                message = state.message,
                onDismiss = {
                    viewModel.onDismissErrorAlertDialog()
                }
            )
        }
    }

    if (state.loading) {
        LoadingDialog(
            onDismissRequest = {}
        )
    }


    FoodOrderingAppRegisterScreen(

        onTapCreateAcc = { fullName, email, password ->

            viewModel.onClickRegister(
                fullName = fullName,
                email = email,
                password = password)


    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodOrderingAppRegisterScreen(onTapCreateAcc: (String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }


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

        Box(modifier = Modifier
            .padding(innerPadding).fillMaxSize()){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM_2)
            ) {

                //title section
                Text(
                    stringResource(Res.string.create_account),
                    fontSize = TEXT_XXLARGE,
                    color = TITLE_BLACK_COLOR,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = MARGIN_XLARGE)
                        .align(Alignment.CenterHorizontally)
                )
                //Name input section
                FoodOrderingAppOutlineTxtField(
                    value = name,
                    onValueChange = { text ->
                        name = text
                    },
                    txt = stringResource(Res.string.name),
                    isPasswordField = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    onImeAction = {
                        emailFocusRequester.requestFocus()
                    },
                    modifier = Modifier
                        .padding(horizontal = MARGIN_MEDIUM_2)
                        .fillMaxWidth())


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
                    onImeAction = {
                        passwordFocusRequester.requestFocus()
                    },
                    modifier = Modifier
                        .padding(horizontal = MARGIN_MEDIUM_2)
                        .fillMaxWidth())

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
                    onImeAction = {
                        focusManager.clearFocus()
                        onTapCreateAcc(name,email,password)
                    },
                    modifier = Modifier
                        .padding(horizontal = MARGIN_MEDIUM_2)
                        .fillMaxWidth()
                )

                //Create Account Button Section
                FoodOrderingAppButton(
                    onTapButton = {
                        onTapCreateAcc(name,email,password)
                    },
                    modifier =
                        Modifier.padding(horizontal = MARGIN_MEDIUM_2, vertical = MARGIN_CARD_MEDIUM_2)
                            .fillMaxWidth().height(48.dp),
                    btnText = stringResource(Res.string.create_account),
                    fontSize = TEXT_REGULAR_2X
                )


            }


            //title section
            Text(
                stringResource(Res.string.terms_of_service),
                fontSize = TEXT_REGULAR_2X,
                color = OUTLINE_TXT_FIELD_TXT_COLOR,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(MARGIN_MEDIUM_2)
                    .align(Alignment.BottomCenter)
            )
        }




    }
}

//@Preview
//@Composable
//fun FoodOrderingAppRegisterScreenPreview() {
//    FoodOrderingAppRegisterScreen(onTapCreateAcc = {})
//}