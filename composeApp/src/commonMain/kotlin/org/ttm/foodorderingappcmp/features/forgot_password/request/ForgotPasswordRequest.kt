package org.ttm.foodorderingappcmp.features.forgot_password.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordRequest(

    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String
)
