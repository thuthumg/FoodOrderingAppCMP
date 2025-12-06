package org.ttm.foodorderingappcmp.features.forgot_password.ui.state

data class ResetPasswordState (
    val loading: Boolean = false,
    val message: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val loginStatus: Boolean = false,
    val showSuccessDialog: Boolean = false
)
