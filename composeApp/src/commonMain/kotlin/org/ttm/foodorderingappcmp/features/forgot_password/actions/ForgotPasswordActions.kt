package org.ttm.foodorderingappcmp.features.forgot_password.actions

import org.ttm.foodorderingappcmp.features.forgot_password.network.responses.CheckEmailResponse

sealed class ForgotPasswordActions{

    class OnTapBack : ForgotPasswordActions()

     class OnTapContinue: ForgotPasswordActions()

    class OnEmailChanged(val email: String): ForgotPasswordActions()

    class OnUnauthorized: ForgotPasswordActions()

    class OnErrorDialogDismissed: ForgotPasswordActions()
}