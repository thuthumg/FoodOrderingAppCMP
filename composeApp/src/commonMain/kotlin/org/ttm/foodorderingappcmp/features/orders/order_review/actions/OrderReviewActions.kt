package org.ttm.foodorderingappcmp.features.orders.order_review.actions

import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

sealed class OrderReviewActions {
    class OnTapBack: OrderReviewActions()
    data class OnTapConfirmOrder(
       val paymentId: Long,
       val  deliveryAddressId: Long,
       val foodItemList: List<FoodItemVO>
    ): OrderReviewActions()
    class  OnErrorDialogDismissed: OrderReviewActions()
}