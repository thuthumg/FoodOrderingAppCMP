package org.ttm.foodorderingappcmp.features.orders.cart.state

import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentListVO
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

data class CartState(
    val foodItemList: List<FoodItemVO> = listOf(),
    val loading: Boolean = false,
    val message: String = "",

    val loginStatus: Boolean = false,



    val showRemoveItemDialog: Boolean = false,
    val removeItem: FoodItemVO? = null,

    val showDeliveryPaymentDialog: Boolean = false,
    val deliveryAddressAndPaymentListVO: DeliveryAddressAndPaymentListVO? = null
)
