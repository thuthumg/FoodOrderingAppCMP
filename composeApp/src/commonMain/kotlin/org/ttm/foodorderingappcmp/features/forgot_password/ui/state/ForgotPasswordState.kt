package org.ttm.foodorderingappcmp.features.forgot_password.ui.state

import org.ttm.foodorderingappcmp.features.forgot_password.network.responses.CheckEmailResponse

data class ForgotPasswordState (
    val checkEmailResponse: CheckEmailResponse ? = null,
    val loading: Boolean = false,
    val message: String = "",
    val loginStatus: Boolean = false,
    val email: String = ""
    //val errorDialogShowStatus: Boolean = false,
)