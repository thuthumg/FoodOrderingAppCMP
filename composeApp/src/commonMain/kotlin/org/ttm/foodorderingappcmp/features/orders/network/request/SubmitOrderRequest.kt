package org.ttm.foodorderingappcmp.features.orders.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

@Serializable
data class SubmitOrderRequest(

    @SerialName("payment_method_id")
    val paymentMethodId: Long,

    @SerialName("delivery_address_id")
    val deliveryAddressId: Long,


    @SerialName("food_items")
    val foodItems: List<FoodItemVO>

)