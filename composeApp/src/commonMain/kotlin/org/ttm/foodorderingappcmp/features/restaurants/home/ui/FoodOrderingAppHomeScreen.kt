package org.ttm.foodorderingappcmp.features.restaurants.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.ttm.foodorderingappcmp.common.ui.CommonAlertDialog
import org.ttm.foodorderingappcmp.common.ui.LoadingDialog
import org.ttm.foodorderingappcmp.core.MARGIN_CARD_MEDIUM_2
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.features.restaurants.home.actions.HomeActions
import org.ttm.foodorderingappcmp.features.restaurants.home.events.HomeEvents
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

    LaunchedEffect(Unit){
        homeViewModel.navigationSharedFlow.collectLatest { events ->
            when(events){
                is HomeEvents.NavigateToCart -> {
                    onNavigateToShoppingCart()
                }
                is HomeEvents.NavigateToRestaurantDetail -> {
                    onNavigateToRestaurantDetail(events.restaurantDetailId)
                }

                is HomeEvents.NavigateToLogin -> {
                    onNavigateToLogin()
                }
            }
        }
    }

    FoodOrderingAppHomeScreen(
        state = homeState,
           onAction = {
               homeViewModel.onAction(it)
           }
    )
}

@Composable
fun FoodOrderingAppHomeScreen(
    state: HomeState,
    onAction: (HomeActions)-> Unit) {


    /************ Loading *****************/
    if (state.loading) {
        LoadingDialog(
            onDismissRequest = {}
        )
    }

    /************* API Call Error State *********************/
    if (state.message.isNotBlank()) {
        CommonAlertDialog(
            title = "Error",
            message = state.message,
            onConfirm = {
                if(state.loginStatus){
                    onAction(HomeActions.OnUnauthorized())
                }else{
                    onAction(HomeActions.OnErrorDialogDismissed())
                }


            }
        )
    }


    /*************** Restaurant List Screen **********************/
    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            HomeTopAppBar(onTapShoppingCart = {
                onAction(HomeActions.OnTapShoppingCart())
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
                        onAction(HomeActions.OnTapOrder(restaurantId))
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