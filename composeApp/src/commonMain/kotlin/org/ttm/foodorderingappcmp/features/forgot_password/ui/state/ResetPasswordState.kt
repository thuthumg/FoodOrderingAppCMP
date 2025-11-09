package org.ttm.foodorderingappcmp.features.forgot_password.ui.state

data class ResetPasswordState (
    val loading: Boolean = false,
    val message: String = "",
    val resetPasswordStatus: Boolean = false,
    val errorDialogShowStatus: Boolean = false,
    val goToLoginStatus: Boolean = false
)
