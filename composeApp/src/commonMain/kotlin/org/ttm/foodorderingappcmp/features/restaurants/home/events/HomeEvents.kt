package org.ttm.foodorderingappcmp.features.restaurants.home.events

sealed class HomeEvents {

    data class NavigateToRestaurantDetail(val restaurantDetailId: Long) : HomeEvents()
    class NavigateToCart: HomeEvents()

    class NavigateToLogin: HomeEvents()
}