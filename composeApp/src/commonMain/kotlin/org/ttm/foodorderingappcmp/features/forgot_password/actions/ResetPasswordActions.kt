package org.ttm.foodorderingappcmp.features.forgot_password.actions

sealed class ResetPasswordActions{
    class OnTapBack: ResetPasswordActions()
    data class OnPasswordChanged(val password: String): ResetPasswordActions()
    data class OnConfirmPasswordChanged(val confirmPassword: String): ResetPasswordActions()
    class OnTapResetPassword: ResetPasswordActions()


    class OnErrorDialogDismissed: ResetPasswordActions()

    class OnUnauthorized: ResetPasswordActions()

    class OnTapOK: ResetPasswordActions()
}