package org.ttm.foodorderingappcmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.auth.ui.FoodOrderingAppLoginScreen
import org.ttm.foodorderingappcmp.auth.ui.FoodOrderingAppRegisterScreen
import org.ttm.foodorderingappcmp.features.orders.cart.ui.CartScreen
import org.ttm.foodorderingappcmp.core.FoodOrderingAppTypography
import org.ttm.foodorderingappcmp.features.forgot_password.ForgotPasswordScreen
import org.ttm.foodorderingappcmp.features.forgot_password.ResetPasswordScreen
import org.ttm.foodorderingappcmp.features.orders.checkout.CheckoutScreen
import org.ttm.foodorderingappcmp.features.orders.confirm_order.ConfirmOrderScreen
import org.ttm.foodorderingappcmp.features.orders.order_review.OrderReviewScreen
import org.ttm.foodorderingappcmp.features.profile.about.AboutScreen
import org.ttm.foodorderingappcmp.features.restaurants.home_navigation.ui.HomeBottomNavigationScreen
import org.ttm.foodorderingappcmp.features.restaurants.detail.ui.RestaurantDetailScreen

@Composable
@Preview
fun App() {
//state
    // var selectedNavItem by remember { mutableStateOf("Home") }

    val navController = rememberNavController()
    MaterialTheme(
        typography = FoodOrderingAppTypography()
    ) {


        NavHost(
            navController = navController,
            startDestination = NavRoutes.Login
        ) {
            composable<NavRoutes.Login> {
                FoodOrderingAppLoginScreen(onTapLogin = {
                    navController.navigate(NavRoutes.Home("Home")){
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                    onTapSignUp = {
                        navController.navigate(NavRoutes.Register) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    },
                    onTapForgotPassword = {
                        navController.navigate(NavRoutes.ForgotPassword)
                    }
                    )
            }

            composable<NavRoutes.Register> {
                FoodOrderingAppRegisterScreen(
                    onTapCreateAcc = {
                        navController.navigate(NavRoutes.Login){
                            popUpTo(NavRoutes.Register::class){
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable<NavRoutes.Home> { backStackEntry ->
                val args = backStackEntry.toRoute<NavRoutes.Home>()
                var selectedNavItem by remember { mutableStateOf(args.selectedPage) }

                HomeBottomNavigationScreen(
                    selectedNavItem = selectedNavItem,
                    onSelectedChange = { selectedItem ->
                        selectedNavItem = selectedItem
                    },
                    onTapOrder = { restaurantId ->
                        navController.navigate(NavRoutes.RestaurantDetail)
                    },
                    onTapAbout = {
                        navController.navigate(NavRoutes.About)
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

            composable<NavRoutes.Cart> {
                CartScreen(
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onTapPlaceOrder = {
                        navController.navigate(NavRoutes.Checkout)
                    }
                )
            }

            composable<NavRoutes.Checkout> {
                CheckoutScreen(
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onTapPlaceOrder = {
                        navController.navigate(NavRoutes.OrderReview)
                    })
            }

            composable<NavRoutes.OrderReview> {
                OrderReviewScreen(
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onTapConfirmOrder = {
                        navController.navigate(NavRoutes.OrderConfirm)
                    })
            }

            composable<NavRoutes.OrderConfirm> {
                ConfirmOrderScreen(
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onTapConfirmOrder = {
                        navController.navigate(NavRoutes.Home("Orders")) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    })
            }

            composable<NavRoutes.ForgotPassword> {
                ForgotPasswordScreen(
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onTapContinue = {
                        navController.navigate(NavRoutes.ResetPassword)
                    }
                )
            }

            composable<NavRoutes.ResetPassword> {
                ResetPasswordScreen(
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onTapResetPassword = {
                        navController.navigate(NavRoutes.Login){
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable<NavRoutes.Profile> {
                ForgotPasswordScreen(
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onTapContinue = {

                    }
                )
            }

            composable<NavRoutes.About> {
                AboutScreen(
                    onTapBack = {
                        navController.navigate(NavRoutes.Home("Profile")) {
                            popUpTo(NavRoutes.Home::class){
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

    }
}

@Serializable
sealed class NavRoutes {

    @Serializable
    object Login

    @Serializable
    object Register

    @Serializable
    data class Home(val selectedPage: String)

    @Serializable
    object RestaurantDetail

    @Serializable
    object Cart

    @Serializable
    object Checkout

    @Serializable
    object OrderReview

    @Serializable
    object OrderConfirm

    @Serializable
    object ForgotPassword

    @Serializable
    object ResetPassword

    @Serializable
    object Profile

    @Serializable
    object About

}