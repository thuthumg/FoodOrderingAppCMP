package org.ttm.foodorderingappcmp.features.orders.order_review.state

import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentVO
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

data class OrderReviewState(
    val shoppingCartList: List<FoodItemVO> = listOf(),
    val loading: Boolean = false,
    val message: String = "",
    //val orderSubmitStatus: Boolean = false,
   // val errorDialogShowStatus: Boolean = false,
    val loginStatus: Boolean = false,
    val deliveryAddressAndPaymentVO: DeliveryAddressAndPaymentVO? = null
)
