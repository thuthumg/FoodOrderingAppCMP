package org.ttm.foodorderingappcmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.RoomDatabase
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.app.viewmodel.AppViewModel
import org.ttm.foodorderingappcmp.auth.ui.FoodOrderingAppLoginScreenRoute
import org.ttm.foodorderingappcmp.auth.ui.FoodOrderingAppRegisterRoute
import org.ttm.foodorderingappcmp.auth.ui.viewmodel.LoginRegisterViewModel
import org.ttm.foodorderingappcmp.core.FoodOrderingAppTypography
import org.ttm.foodorderingappcmp.core.persistence.AppDatabase
import org.ttm.foodorderingappcmp.core.persistence.AppDatabaseProvider
import org.ttm.foodorderingappcmp.core.utils.apiToken
import org.ttm.foodorderingappcmp.features.forgot_password.ForgotPasswordScreen
import org.ttm.foodorderingappcmp.features.forgot_password.ResetPasswordScreen
import org.ttm.foodorderingappcmp.features.orders.cart.ui.CartRoute
import org.ttm.foodorderingappcmp.features.orders.cart.viewmodel.CartViewModel
import org.ttm.foodorderingappcmp.features.orders.checkout.CheckoutScreen
import org.ttm.foodorderingappcmp.features.orders.confirm_order.ConfirmOrderScreen
import org.ttm.foodorderingappcmp.features.orders.order_review.OrderReviewScreen
import org.ttm.foodorderingappcmp.features.profile.about.AboutScreen
import org.ttm.foodorderingappcmp.features.restaurants.detail.ui.RestaurantDetailRoute
import org.ttm.foodorderingappcmp.features.restaurants.home_navigation.ui.HomeBottomNavigationScreen
import org.ttm.foodorderingappcmp.features.restaurants.detail.viewmodel.RestaurantDetailViewModel

@Composable
@Preview
fun App(databaseBuilder: RoomDatabase.Builder<AppDatabase>) {

    AppDatabaseProvider.initializeDatabase(databaseBuilder)

    val navController = rememberNavController()

    MaterialTheme(
        typography = FoodOrderingAppTypography()
    ) {

        val appViewModel = viewModel { AppViewModel() }
        val state by appViewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            appViewModel.autoLogin()
        }


        val startDestinationPoint =
            if (state.loginStatus) {
                NavRoutes.Home("Home")
            } else {
                NavRoutes.Login
            }


        NavHost(
            navController = navController,
            startDestination =  startDestinationPoint
        ) {
            composable<NavRoutes.Login> {
                val loginRegisterViewModel = viewModel { LoginRegisterViewModel() }

                FoodOrderingAppLoginScreenRoute(
                    loginRegisterViewModel,
                    onNavigateHome = {
                        navController.navigate(NavRoutes.Home("Home")) {
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
                val loginRegisterViewModel = viewModel { LoginRegisterViewModel() }
                FoodOrderingAppRegisterRoute(
                    viewModel = loginRegisterViewModel,
                    onNavigateHome = {
                        navController.navigate(NavRoutes.Home("Home")) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                )

            }

            composable<NavRoutes.Home> { backStackEntry ->
                val args = backStackEntry.toRoute<NavRoutes.Home>()
                var selectedNavItem by remember { mutableStateOf(args.selectedPage) }
                val loginRegisterViewModel = viewModel { LoginRegisterViewModel() }

                HomeBottomNavigationScreen(
                    selectedNavItem = selectedNavItem,
                    onSelectedChange = { selectedItem ->
                        selectedNavItem = selectedItem
                    },
                    onTapOrder = { restaurantId ->
                        navController.navigate(NavRoutes.RestaurantDetail(restaurantId = restaurantId))
                    },
                    onTapAbout = {
                        navController.navigate(NavRoutes.About)
                    },
                    onNavigateToLogin = {
                        apiToken = ""
                        loginRegisterViewModel.clearUserData()

                        navController.navigate(NavRoutes.Login) {
                            popUpTo(NavRoutes.Home::class) {
                                inclusive = true
                            }
                        }
                    },
                    onTapShoppingCart = {
                        navController.navigate(NavRoutes.Cart)
                    }
                )
            }

            composable<NavRoutes.RestaurantDetail> { backStackEntry ->
                val args = backStackEntry.toRoute<NavRoutes.RestaurantDetail>()

                val viewModel = viewModel {RestaurantDetailViewModel(args.restaurantId)}

                RestaurantDetailRoute(
                    restaurantViewModel = viewModel,
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onTapViewMyCart = {
                        navController.navigate(NavRoutes.Cart)
                    }
                )

            }

            composable<NavRoutes.Cart> {
                val cartViewModel =  viewModel { CartViewModel() }
                CartRoute(
                    cartViewModel = cartViewModel,
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onTapPlaceOrder = {
                        navController.navigate(NavRoutes.Checkout)
                    },
                    onTapOrderNow = {
                        navController.navigate(NavRoutes.Home("Home")) {
                            popUpTo(NavRoutes.Home::class) {
                                inclusive = true
                            }
                        }
                    })
//                CartScreen(
//                    onTapBack = {
//                        navController.navigateUp()
//                    },
//                    onTapPlaceOrder = {
//                        navController.navigate(NavRoutes.Checkout)
//                    },
//                    onTapOrderNow = {}
//                )
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
                        navController.navigate(NavRoutes.Login) {
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
                            popUpTo(NavRoutes.Home::class) {
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
    data class RestaurantDetail(val restaurantId: Long)

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