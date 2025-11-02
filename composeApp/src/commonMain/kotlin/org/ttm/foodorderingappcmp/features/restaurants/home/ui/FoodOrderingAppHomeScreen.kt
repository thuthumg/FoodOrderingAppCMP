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
import org.ttm.foodorderingappcmp.common.ui.ErrorAlertDialog
import org.ttm.foodorderingappcmp.common.ui.LoadingDialog
import org.ttm.foodorderingappcmp.core.MARGIN_CARD_MEDIUM_2
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.RestaurantVO
import org.ttm.foodorderingappcmp.features.restaurants.home.viewmodel.HomeViewModel
import org.ttm.foodorderingappcmp.features.restaurants.home_navigation.ui.HomeTopAppBar

@Composable
fun FoodOrderingAppHomeRoute(viewModel: HomeViewModel,
                             onTapOrder : (Long) -> Unit,
                             onNavigateToLogin: () -> Unit,
                             onTapShoppingCart: () -> Unit) {


    val homeState by viewModel.homeState.collectAsStateWithLifecycle()

    if (homeState.loading) {
        LoadingDialog(
            onDismissRequest = {}
        )
    }

    if(homeState.goToLogin){
        onNavigateToLogin()
    }

     if (homeState.message.isNotBlank() && !(homeState.dismissStatus)) {

            ErrorAlertDialog(
                showDialog = true,
                title = "Error",
                message = homeState.message,
                onDismiss = {
                    viewModel.onDismissErrorAlertDialog()

                }
            )
        }



    FoodOrderingAppHomeScreen(
        restaurantList = homeState.restaurantVO,
        onTapOrder = { restaurantId ->
            onTapOrder(restaurantId)
    },
        onTapShoppingCart = {
            onTapShoppingCart()
        })
}
@Composable
fun FoodOrderingAppHomeScreen(restaurantList: List<RestaurantVO>, onTapOrder: (Long) -> Unit,
                              onTapShoppingCart:() -> Unit) {

    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            HomeTopAppBar(onTapShoppingCart = {
                onTapShoppingCart()
            })
        },
        modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(MARGIN_CARD_MEDIUM_2),
            contentPadding = PaddingValues(bottom = 88.dp)
        ){
            items(restaurantList.size){
                RestaurantItemSection(restaurantList = restaurantList,
                    index = it,
                    onTapOrder = { restaurantId ->
                        onTapOrder(restaurantId)

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