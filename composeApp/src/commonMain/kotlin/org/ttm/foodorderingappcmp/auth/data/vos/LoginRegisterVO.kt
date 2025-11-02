package org.ttm.foodorderingappcmp.auth.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity("users")
@Serializable
data class LoginRegisterVO(

    @PrimaryKey(autoGenerate = false)
    @SerialName("id")
    val id: Long,

    @ColumnInfo("email")
    @SerialName("email")
    val email: String,

    @ColumnInfo("fullname")
    @SerialName("fullname")
    val fullName: String,

    @ColumnInfo("created_at")
    @SerialName("created_at")
    val createdAt: String,

    @ColumnInfo("updated_at")
    @SerialName("updated_at")
    val updatedAt: String,


    @ColumnInfo("access_token")
    @SerialName("access_token")
    val accessToken: String,
)

