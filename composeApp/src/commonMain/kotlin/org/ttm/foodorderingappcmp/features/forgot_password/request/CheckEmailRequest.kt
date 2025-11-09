package org.ttm.foodorderingappcmp.features.forgot_password.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckEmailRequest(

    @SerialName("email")
    val email : String
)
