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
import kotlinx.coroutines.delay
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
import org.ttm.foodorderingappcmp.features.forgot_password.ForgotPasswordRoute
import org.ttm.foodorderingappcmp.features.forgot_password.ForgotPasswordScreen
import org.ttm.foodorderingappcmp.features.forgot_password.ResetPasswordRoute
import org.ttm.foodorderingappcmp.features.forgot_password.ResetPasswordScreen
import org.ttm.foodorderingappcmp.features.forgot_password.ui.viewmodel.ForgotPasswordViewModel
import org.ttm.foodorderingappcmp.features.forgot_password.ui.viewmodel.ResetPasswordViewModel
import org.ttm.foodorderingappcmp.features.orders.cart.ui.CartRoute
import org.ttm.foodorderingappcmp.features.orders.cart.viewmodel.CartViewModel
import org.ttm.foodorderingappcmp.features.orders.checkout.ui.CheckoutRoute
import org.ttm.foodorderingappcmp.features.orders.checkout.viewmodel.CheckoutViewModel
import org.ttm.foodorderingappcmp.features.orders.confirm_order.ConfirmOrderScreen
import org.ttm.foodorderingappcmp.features.orders.order_review.ui.OrderReviewRoute
import org.ttm.foodorderingappcmp.features.orders.order_review.viewmodel.OrderReviewViewModel
import org.ttm.foodorderingappcmp.features.profile.about.AboutScreen
import org.ttm.foodorderingappcmp.features.restaurants.detail.ui.RestaurantDetailRoute
import org.ttm.foodorderingappcmp.features.restaurants.home_navigation.ui.HomeBottomNavigationScreen
import org.ttm.foodorderingappcmp.features.restaurants.detail.viewmodel.RestaurantDetailViewModel
import org.ttm.foodorderingappcmp.splash.SplashScreen

@Composable
@Preview
fun App(databaseBuilder: RoomDatabase.Builder<AppDatabase>) {

    AppDatabaseProvider.initializeDatabase(databaseBuilder)

    val navController = rememberNavController()



    /******************Auto Login Section**********************/
    // ViewModel
    val appViewModel: AppViewModel = viewModel()

    // Collect state from ViewModel
    val state by appViewModel.state.collectAsStateWithLifecycle()
    var startDestinationPoint: Any by remember { mutableStateOf(NavRoutes.Splash) }

    LaunchedEffect(Unit){
        delay(3000)
        startDestinationPoint = if(state.loginStatus) NavRoutes.Home("Home") else NavRoutes.Login
    }

//    val startDestinationState = remember {
//        mutableStateOf<NavRoutes>(NavRoutes.Splash as NavRoutes)
//    }
//
//    LaunchedEffect(Unit) {
//        delay(3000)
//        startDestinationState.value =
//            if (state.loginStatus) NavRoutes.Home("Home")
//            else NavRoutes.Login
//    }
//
//    val startDestination = startDestinationState.value


    MaterialTheme(
        typography = FoodOrderingAppTypography()
    ) {


        /******************NavHost**********************/

        NavHost(
            navController = navController,
            startDestination =  startDestinationPoint
        ) {
            /******** Splash *************/

            composable<NavRoutes.Splash>{
                SplashScreen()
            }

            /******** Login *************/
            composable<NavRoutes.Login> {
                val loginRegisterViewModel = viewModel { LoginRegisterViewModel() }

                FoodOrderingAppLoginScreenRoute(
                    loginRegisterViewModel,
                    onNavigateToHome = {
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


            /******** Register *************/
            composable<NavRoutes.Register> {
                val loginRegisterViewModel = viewModel { LoginRegisterViewModel() }
                FoodOrderingAppRegisterRoute(
                    loginRegisterViewModel = loginRegisterViewModel,
                    onNavigateToHome = {
                        navController.navigate(NavRoutes.Home("Home")) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                )

            }


            /******** Home *************/
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
                    },
                    onNavigateToLogout = {
                        apiToken = ""
                        loginRegisterViewModel.clearUserData()

                        navController.navigate(NavRoutes.Login) {
                            popUpTo(NavRoutes.Home::class) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            /******** Restaurant Detail *************/
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

            /******** Add to Cart *************/
            composable<NavRoutes.Cart> {
                val cartViewModel =  viewModel { CartViewModel() }
                CartRoute(
                    cartViewModel = cartViewModel,
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onNavigateToCheckout = {
                        navController.navigate(NavRoutes.Checkout)
                    },
                    onTapOrderNow = {
                        navController.navigate(NavRoutes.Home("Home")) {
                            popUpTo(NavRoutes.Home::class) {
                                inclusive = true
                            }
                        }
                    },
                    onNavigateToReviewOrder = {
                        navController.navigate(NavRoutes.OrderReview)
                    })

            }

            /******** Checkout *************/
            composable<NavRoutes.Checkout> {
                val checkoutViewModel =  viewModel { CheckoutViewModel() }
                CheckoutRoute(
                    checkoutViewModel = checkoutViewModel,
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onNavigateToOrderReview = {
                        navController.navigate(NavRoutes.OrderReview)
                    }
                )

            }

            /******** OrderReview *************/
            composable<NavRoutes.OrderReview> {
                val orderReviewViewModel = viewModel { OrderReviewViewModel() }
                OrderReviewRoute(
                    viewModel = orderReviewViewModel,
                    onTapBack = {
                        navController.navigateUp()
                },
                    onNavigateToOrderConfirmation = {
                        navController.navigate(NavRoutes.OrderConfirm)
                    })

            }

            /******** OrderConfirm *************/
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

            /******** Forgot Password *************/
            composable<NavRoutes.ForgotPassword> {

                val viewModel = viewModel{ ForgotPasswordViewModel() }

                ForgotPasswordRoute(
                    forgotPasswordViewModel = viewModel,
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onNavigateToResetPassword = { email ->
                        navController.navigate(NavRoutes.ResetPassword(email))
                    }
                )


            }

            /******** Reset Password *************/
            composable<NavRoutes.ResetPassword> { backStackEntry ->
                val args = backStackEntry.toRoute<NavRoutes.ResetPassword>()

                val viewModel = viewModel { ResetPasswordViewModel(args.email)}


                ResetPasswordRoute(
                    resetPasswordViewModel = viewModel,
                    onTapBack = {
                        navController.navigateUp()
                    },
                    onNavigateToLogin = {
                        navController.navigate(NavRoutes.Login) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                )

            }

            /******** Profile *************/
            composable<NavRoutes.Profile> {
//                ForgotPasswordScreen(
//                    onTapBack = {
//                        navController.navigateUp()
//                    },
//                    onTapContinue = {
//
//                    }
//                )
            }

            /******** About *************/
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
    object Splash

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
    data class ResetPassword(val email: String)

    @Serializable
    object Profile

    @Serializable
    object About

}