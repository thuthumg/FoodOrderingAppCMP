package org.ttm.foodorderingappcmp.features.restaurants.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.ttm.foodorderingappcmp.common.ui.CommonAlertDialog
import org.ttm.foodorderingappcmp.common.ui.LoadingDialog
import org.ttm.foodorderingappcmp.core.MARGIN_CARD_MEDIUM_2
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.features.restaurants.home.state.HomeState
import org.ttm.foodorderingappcmp.features.restaurants.home.viewmodel.HomeViewModel
import org.ttm.foodorderingappcmp.features.restaurants.home_navigation.ui.HomeTopAppBar

@Composable
fun FoodOrderingAppHomeRoute(
    homeViewModel: HomeViewModel,
    onNavigateToRestaurantDetail: (Long) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToShoppingCart: () -> Unit,
) {


    val homeState by homeViewModel.homeState.collectAsStateWithLifecycle()

    FoodOrderingAppHomeScreen(
        state = homeState,
        onNavigateToRestaurantDetail = { restaurantId ->
            onNavigateToRestaurantDetail(restaurantId)
        },
        onNavigateToShoppingCart = {
            onNavigateToShoppingCart()
        },
        onDismissErrorAlertDialog = {
            homeViewModel.onDismissErrorAlertDialog()
        },
        onNavigateToLogin = onNavigateToLogin
    )
}

@Composable
fun FoodOrderingAppHomeScreen(
    state: HomeState,
    onNavigateToRestaurantDetail: (Long) -> Unit,
    onNavigateToShoppingCart: () -> Unit,
    onDismissErrorAlertDialog: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {


    /************ Loading *****************/
    if (state.loading) {
        LoadingDialog(
            onDismissRequest = {}
        )
    }

    /************ Go To Login *****************/
    if (!(state.loginStatus)) {
        onNavigateToLogin()
    }

    /************* API Call Error State *********************/
    if (state.message.isNotBlank() && (state.errorDialogShowStatus)) {
        CommonAlertDialog(
            title = "Error",
            message = state.message,
            onConfirm = {
                onDismissErrorAlertDialog()

            }
        )
    }


    /*************** Restaurant List Screen **********************/
    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            HomeTopAppBar(onTapShoppingCart = {
                onNavigateToShoppingCart()
            })
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            ),
            verticalArrangement = Arrangement.spacedBy(MARGIN_CARD_MEDIUM_2),
            contentPadding = PaddingValues(bottom = 88.dp)
        ) {
            //Restaurant List
            items(state.restaurantList.size) { index ->
                RestaurantItemSection(
                    restaurantVO = state.restaurantList[index],
                    onTapOrder = { restaurantId ->
                        onNavigateToRestaurantDetail(restaurantId)
                    })
            }
        }
    }


}


//@Preview
//@Composable
//fun FoodOrderingAppHomeScreenPreview() {
//    FoodOrderingAppHomeScreen(modifier = Modifier, onTapOrder = {})
//}