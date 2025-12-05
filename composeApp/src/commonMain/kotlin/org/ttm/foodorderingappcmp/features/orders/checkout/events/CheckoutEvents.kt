package org.ttm.foodorderingappcmp.features.orders.checkout.events

sealed class CheckoutEvents{
    class OnNavigateToCart: CheckoutEvents()
    class OnNavigateToOrderReview: CheckoutEvents()
}