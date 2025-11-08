package org.ttm.foodorderingappcmp.features.orders.data.vos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeliveryAddressVO(

    @SerialName("id")
    val id: Long? = null,

    @SerialName("street_address")
    val streetAddress: String,


    @SerialName("created_at")
    val createdAt: String? = null,


    @SerialName("updated_at")
    val updatedAt: String? = null
)
