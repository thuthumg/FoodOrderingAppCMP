package org.ttm.foodorderingappcmp.features.orders.checkout.state

import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentVO

data class CheckoutState(
    val deliveryAddressAndPaymentVO: DeliveryAddressAndPaymentVO? = null,
    val loading: Boolean = false,
    val message: String = "",
    val checkoutApiStatus: Boolean = false,
    val errorDialogShowStatus: Boolean = false
)
