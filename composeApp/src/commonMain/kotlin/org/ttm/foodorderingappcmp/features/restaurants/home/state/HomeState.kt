package org.ttm.foodorderingappcmp.features.restaurants.home.state

import org.ttm.foodorderingappcmp.features.restaurants.data.vos.RestaurantVO

data class HomeState (
    val restaurantVO: List<RestaurantVO> = listOf(),
    val loading: Boolean = false,
    val message: String = "",
    val successStatus: Boolean = false,
    val dismissStatus: Boolean = true,
    val goToLogin: Boolean = false
)