package org.ttm.foodorderingappcmp.features.orders.checkout.state

import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentVO

data class CheckoutState(
    val deliveryAddressAndPaymentVO: DeliveryAddressAndPaymentVO? = null,
    val loading: Boolean = false,
    val message: String = "",
    val cardNumber: String = "",
    val expiryDate: String = "",
    val cvv: String = "",
    val nameOnCard: String = "",
    val deliveryAddress: String = "",
    val loginStatus: Boolean = false
)
