package org.ttm.foodorderingappcmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.auth.ui.FoodOrderingAppLoginScreen
import org.ttm.foodorderingappcmp.auth.ui.FoodOrderingAppRegisterScreen
import org.ttm.foodorderingappcmp.features.orders.cart.ui.CartScreen
import org.ttm.foodorderingappcmp.core.FoodOrderingAppTypography
import org.ttm.foodorderingappcmp.features.orders.checkout.CheckoutScreen
import org.ttm.foodorderingappcmp.features.restaurants.home_navigation.ui.HomeBottomNavigationScreen
import org.ttm.foodorderingappcmp.features.restaurants.detail.ui.RestaurantDetailScreen

@Composable
@Preview
fun App() {

    val navController = rememberNavController()
    MaterialTheme(
        typography = FoodOrderingAppTypography()
    ) {


        NavHost(
            navController = navController,
            startDestination = NavRoutes.Register
        ){
            composable<NavRoutes.Login> {
                FoodOrderingAppLoginScreen(onTapLogin = {
                    navController.navigate(NavRoutes.Home)
                })
            }

            composable<NavRoutes.Register> {
                FoodOrderingAppRegisterScreen(
                    onTapCreateAcc = {
                        navController.navigate(NavRoutes.Login)
                    }
                )
            }

            composable<NavRoutes.Home> {
                HomeBottomNavigationScreen(
                    onTapOrder = {
                        restaurantId ->
                        navController.navigate(NavRoutes.RestaurantDetail)
                    }
                )
            }

            composable<NavRoutes.RestaurantDetail> {
                RestaurantDetailScreen(
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onTapViewMyCart = {
                        navController.navigate(NavRoutes.Cart)
                    }
                )
            }

            composable<NavRoutes.Cart>{
                CartScreen (
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onTapPlaceOrder = {
                        navController.navigate(NavRoutes.Checkout)
                    }
                )
            }

            composable<NavRoutes.Checkout>{
                CheckoutScreen(
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onTapPlaceOrder = {})
            }
        }

    }
}

@Serializable
sealed class NavRoutes{

    @Serializable
    object Login

    @Serializable
    object Register

    @Serializable
    object Home

    @Serializable
    object RestaurantDetail

    @Serializable
    object Cart

    @Serializable
    object Checkout
}