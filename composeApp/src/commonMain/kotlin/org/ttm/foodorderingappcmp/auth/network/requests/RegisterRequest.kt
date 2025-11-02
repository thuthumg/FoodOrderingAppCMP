package org.ttm.foodorderingappcmp.auth.network.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(

    @SerialName("email")
    val email: String,

    @SerialName("fullname")
    val fullName : String,

    @SerialName("password")
    val password : String
)