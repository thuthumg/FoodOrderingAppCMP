package org.ttm.foodorderingappcmp.features.orders.cart.state

import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

data class CartState(
    val foodItemList: List<FoodItemVO> = listOf(),
    val loading: Boolean = false,
    val message: String = "",
    val successStatus: Boolean = false,
    val dismissStatus: Boolean = true,
    val showRemoveItemDialog: Boolean = false,
    val removeItem: FoodItemVO? = null
)
