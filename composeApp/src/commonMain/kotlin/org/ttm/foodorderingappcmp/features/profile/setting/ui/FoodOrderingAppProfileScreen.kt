package org.ttm.foodorderingappcmp.features.profile.setting.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.about
import foodorderingappcmp.composeapp.generated.resources.change_password
import foodorderingappcmp.composeapp.generated.resources.edit_profile
import foodorderingappcmp.composeapp.generated.resources.logout
import foodorderingappcmp.composeapp.generated.resources.notifications
import foodorderingappcmp.composeapp.generated.resources.payment_method
import foodorderingappcmp.composeapp.generated.resources.profile
import foodorderingappcmp.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.common.ui.CommonAlertDialog
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppButton
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppTopAppBar
import org.ttm.foodorderingappcmp.core.MARGIN_LARGE
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_3
import org.ttm.foodorderingappcmp.core.MARGIN_XLARGE
import org.ttm.foodorderingappcmp.core.MARGIN_XXLARGE
import org.ttm.foodorderingappcmp.core.OUTLINE_TXT_FIELD_TXT_COLOR
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_3X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR
import org.ttm.foodorderingappcmp.features.profile.state.ProfileState
import org.ttm.foodorderingappcmp.features.profile.viewmodel.ProfileViewModel

@Composable
fun FoodOrderingAppProfileRoute(profileViewModel: ProfileViewModel,
                                onNavigateToLogout: () -> Unit,
                                onTapAbout: () -> Unit) {

    val state  by profileViewModel.state.collectAsStateWithLifecycle()

    FoodOrderingAppProfileScreen(
        profileState = state,
        onTapLogout = {
            profileViewModel.onDismissErrorAlertDialog() },
        onTapAbout = onTapAbout,
        onNavigateToLogout = onNavigateToLogout
    )

}
@Composable
fun FoodOrderingAppProfileScreen(
    profileState: ProfileState,
    onTapLogout: () -> Unit,
    onTapAbout:() -> Unit,
    onNavigateToLogout: () -> Unit) {

    /************** Logout Status *********************/
    if(profileState.logoutStatus){
        CommonAlertDialog(
            title = "",
            message = "Are you sure you want to log out? All locally stored app data will be lost.",
            onConfirm = {
                onNavigateToLogout()
            }
        )
    }





    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            FoodOrderingAppTopAppBar(
                stringResource(Res.string.profile),
                showBack = false,
                onTapBack = {

                })
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding).fillMaxSize(),
        ) {
            Column(modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2)
            ) {

                //title section
                Text(
                    profileState.userName,
                    fontSize = TEXT_REGULAR_3X,
                    color = TITLE_BLACK_COLOR,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = MARGIN_MEDIUM_2)
                )

                //title section
                Text(
                    profileState.email,
                    fontSize = TEXT_REGULAR_2X,
                    color = OUTLINE_TXT_FIELD_TXT_COLOR,
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.height(MARGIN_XXLARGE))
                Column(
                    verticalArrangement = Arrangement.spacedBy(MARGIN_XLARGE)
                ) {

                    //Edit Profile
                    ProfileItemRow(stringResource(Res.string.edit_profile),
                        Icons.AutoMirrored.Default.ArrowForward, onTapItem = {

                        })

                    //Change Password
                    ProfileItemRow(stringResource(Res.string.change_password),
                        Icons.AutoMirrored.Default.ArrowForward, onTapItem = {

                        })

                    //Payment Methods
                    ProfileItemRow(stringResource(Res.string.payment_method),
                        Icons.AutoMirrored.Default.ArrowForward, onTapItem = {

                        })
                    //Notifications
                    ProfileItemRow(stringResource(Res.string.notifications),
                        Icons.AutoMirrored.Default.ArrowForward, onTapItem = {

                        })
                    //Settings
                    ProfileItemRow(stringResource(Res.string.settings),
                        Icons.AutoMirrored.Default.ArrowForward, onTapItem = {

                        })

                    //About
                    ProfileItemRow(stringResource(Res.string.about),
                        Icons.AutoMirrored.Default.ArrowForward, onTapItem = {
                            onTapAbout()
                        })
                }



            }

            //Logout Button Section
            FoodOrderingAppButton(
                onTapButton = {
                    onTapLogout()
                },
                modifier =
                    Modifier
                        .padding(
                            horizontal = MARGIN_MEDIUM_2,
                            vertical = MARGIN_LARGE
                        )
                        .fillMaxWidth()
                        .height(48.dp).align(Alignment.BottomCenter).offset(y = (-88).dp),
                btnText = stringResource(Res.string.logout),
                fontSize = TEXT_REGULAR_2X,
                buttonContainerColor = Color(242, 232, 232),
                txtColor = TITLE_BLACK_COLOR,
                fontWeight = FontWeight.Bold

            )
        }


    }
}

@Composable
private fun ProfileItemRow(str: String, icon: ImageVector,onTapItem: () -> Unit) {
    Row {

        Text(
            str,
            fontSize = TEXT_REGULAR_2X,
            color = TITLE_BLACK_COLOR,
            modifier = Modifier.weight(1f)
        )
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.clickable{
                onTapItem()
            }
        )
    }
}

//@Preview
//@Composable
//fun FoodOrderingAppProfileScreenPreview() {
//    val profileViewModel = viewModel { ProfileViewModel() }
//    val state  by profileViewModel.state.collectAsStateWithLifecycle()
//    FoodOrderingAppProfileScreen( state ,onTapLogout = {}, onTapAbout = {})
//}