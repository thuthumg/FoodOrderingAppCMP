package org.ttm.foodorderingappcmp.features.restaurants.data.vos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodCategoryVO(

    @SerialName("id")
    val id: Long,

    @SerialName("name")
    val name: String,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("food_items")
    val foodItems: List<FoodItemVO>
)
