package org.ttm.foodorderingappcmp.auth.network.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.ttm.foodorderingappcmp.auth.data.vos.LoginRegisterVO

@Serializable
data class LoginRegisterResponse(
    @SerialName("id")
    val id: Long,

    @SerialName("email")
    val email: String,

    @SerialName("fullname")
    val fullName: String,

    @SerialName("created_at")
    val createdAt: String,


    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("access_token")
    val accessToken: String,
)


fun LoginRegisterResponse.toVO(): LoginRegisterVO {
    return LoginRegisterVO(
        id = this.id,
        email = this.email,
        fullName = this.fullName,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        accessToken = this.accessToken
    )
}