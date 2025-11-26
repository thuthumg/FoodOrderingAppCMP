package org.ttm.foodorderingappcmp.features.restaurants.home.state

import org.ttm.foodorderingappcmp.features.restaurants.data.vos.RestaurantVO

data class HomeState (
    val restaurantList: List<RestaurantVO> = listOf(),
    val loading: Boolean = false,
    val message: String = "",
    val loginStatus: Boolean = false
)