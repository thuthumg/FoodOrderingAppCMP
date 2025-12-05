package org.ttm.foodorderingappcmp.features.forgot_password.events

import org.ttm.foodorderingappcmp.features.forgot_password.network.responses.CheckEmailResponse

sealed class ForgotPasswordEvents{

    class NavigateToHome: ForgotPasswordEvents()

    data class NavigateToResetPassword(val data: String): ForgotPasswordEvents()

    class NavigateToLogin: ForgotPasswordEvents()
}