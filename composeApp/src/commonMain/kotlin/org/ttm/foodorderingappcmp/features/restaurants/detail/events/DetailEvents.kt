package org.ttm.foodorderingappcmp.features.restaurants.detail.events

sealed class DetailEvents {

    class NavigateToHome: DetailEvents()
    class NavigateToCart: DetailEvents()
    class NavigateToLogin: DetailEvents()

}