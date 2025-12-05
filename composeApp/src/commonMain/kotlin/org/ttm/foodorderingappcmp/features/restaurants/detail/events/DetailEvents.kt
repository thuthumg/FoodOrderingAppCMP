package org.ttm.foodorderingappcmp.features.restaurants.detail.events

sealed class DetailEvents {

    class NavigateToHome: DetailEvents()
    class NavigateToCart: DetailEvents()
    class NavigateToLogin: DetailEvents()

    class ScrollToTab(val selectedIndex: Int): DetailEvents()


}