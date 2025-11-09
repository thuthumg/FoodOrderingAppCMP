package org.ttm.foodorderingappcmp.features.forgot_password.network.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.ttm.foodorderingappcmp.auth.data.vos.LoginRegisterVO

@Serializable
data class CheckEmailResponse(

    @SerialName("user")
    val user: LoginRegisterVO,

    @SerialName("reset_password_token")
    val resetPasswordToken: String
)