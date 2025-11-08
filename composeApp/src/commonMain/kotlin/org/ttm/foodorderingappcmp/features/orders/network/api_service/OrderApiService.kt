package org.ttm.foodorderingappcmp.features.orders.network.api_service

import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentListVO
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentVO
import org.ttm.foodorderingappcmp.features.orders.network.request.SubmitOrderRequest
import org.ttm.foodorderingappcmp.features.orders.data.vos.OrderItemVO

interface OrderApiService {

    suspend fun addDeliveryAddressAndPayment(cardNumber: String,
                                             expireDate: String,
                                             cvv: String,
                                             nameOnCard: String,
                                             deliveryAddress: String): DeliveryAddressAndPaymentVO


    suspend fun getDeliveryAddressesAndPaymentMethods(): DeliveryAddressAndPaymentListVO

    suspend fun submitOrder(submitOrderRequest: SubmitOrderRequest): OrderItemVO

    suspend fun getOrdersForUser(): List<OrderItemVO>?
}