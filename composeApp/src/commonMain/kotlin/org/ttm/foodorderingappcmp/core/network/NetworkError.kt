package org.ttm.foodorderingappcmp.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NetworkError(

    @SerialName("statusCode")
    val statusCode: Int? = null,

    @SerialName("statusMessage")
    val statusMessage: String? = null,

    @SerialName("message")
    val message: String? = null,

    @SerialName("success")
    val success: Boolean? = null,

    @SerialName("error")
    val error: String? = null
)
