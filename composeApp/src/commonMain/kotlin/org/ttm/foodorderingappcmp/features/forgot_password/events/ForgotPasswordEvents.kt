package org.ttm.foodorderingappcmp.features.forgot_password.events

sealed class ForgotPasswordEvents{

    class NavigateToHome: ForgotPasswordEvents()

    data class NavigateToResetPassword(val data: String): ForgotPasswordEvents()

    class NavigateToLogin: ForgotPasswordEvents()
}