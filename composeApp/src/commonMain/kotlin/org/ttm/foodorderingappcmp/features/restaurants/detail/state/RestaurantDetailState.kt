package org.ttm.foodorderingappcmp.features.restaurants.detail.state

import org.ttm.foodorderingappcmp.features.restaurants.data.vos.RestaurantVO


data class RestaurantDetailState (
    val restaurantVO: RestaurantVO? = null,
    val loading: Boolean = false,
    val message: String = "",
    val showViewMyCart: Boolean = false,
    val successStatus: Boolean = false,
    val dismissStatus: Boolean = true
)