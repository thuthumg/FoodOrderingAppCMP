package org.ttm.foodorderingappcmp.features.restaurants.data.vos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantCategoryVO(

    @SerialName("id")
    val id: Long,

    @SerialName("name")
    val name: String,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String
)
