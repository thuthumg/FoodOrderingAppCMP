package org.ttm.foodorderingappcmp.auth.events


sealed class LoginEvents {
    class NavigateToHome : LoginEvents()
    class NavigateToSignUp: LoginEvents()
    class NavigateToForgotPassword: LoginEvents()
}