package org.ttm.foodorderingappcmp.features.forgot_password.events

sealed class ResetPasswordEvents{
    class NavigateToForgotPassword: ResetPasswordEvents()
    class NavigateToLogin: ResetPasswordEvents()
}