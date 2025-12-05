package org.ttm.foodorderingappcmp.features.orders.cart.events


sealed class CartEvents{
    class OnNavigateToDetail: CartEvents()
    //class OnNavigateToDeliveryPaymentAlert: CartEvents()
    class OnNavigateToCheckOut: CartEvents()
    class OnNavigateToReviewOrder: CartEvents()
    class OnNavigateToHome: CartEvents()
    class OnNavigateToLogin: CartEvents()

}