package org.ttm.foodorderingappcmp.features.restaurants.data.vos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantVO(

    @SerialName("id")
    val id: Long,

    @SerialName("name")
    val name: String,

    @SerialName("image_url")
    val imageUrl: String,

    @SerialName("average_rating")
    val averageRating : Double,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("restaurant_categories")
    val restaurantCategories: List<RestaurantCategoryVO>?,

    @SerialName("food_categories")
    val foodCategories: List<FoodCategoryVO>?


)
