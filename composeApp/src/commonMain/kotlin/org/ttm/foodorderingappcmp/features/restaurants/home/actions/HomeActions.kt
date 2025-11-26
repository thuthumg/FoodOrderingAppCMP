package org.ttm.foodorderingappcmp.features.restaurants.home.actions

sealed class HomeActions {
    data class OnTapOrder(val restaurantDetailId: Long) : HomeActions()
    class OnTapShoppingCart: HomeActions()

    class OnErrorDialogDismissed: HomeActions()

    class OnUnauthorized: HomeActions()
}