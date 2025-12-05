package org.ttm.foodorderingappcmp.features.orders.order_review.events

sealed class OrderReviewEvents{
    class OnNavigateToOrderConfirm: OrderReviewEvents()
    class OnNavigateToCart: OrderReviewEvents()
}