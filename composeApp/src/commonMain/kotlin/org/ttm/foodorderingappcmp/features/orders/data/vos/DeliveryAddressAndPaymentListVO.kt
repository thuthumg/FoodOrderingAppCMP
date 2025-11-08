package org.ttm.foodorderingappcmp.features.orders.data.vos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeliveryAddressAndPaymentListVO(

    @SerialName("payment_methods")
    val paymentMethods: List<PaymentVO>,

    @SerialName("delivery_addresses")
    val deliveryAddresses: List<DeliveryAddressVO>
)
