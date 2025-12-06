package org.ttm.foodorderingappcmp.features.orders.network.api_service

import org.ttm.foodorderingappcmp.core.network.FoodOrderingError
import org.ttm.foodorderingappcmp.core.network.FoodOrderingResult
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentListVO
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentVO
import org.ttm.foodorderingappcmp.features.orders.data.vos.OrderItemVO
import org.ttm.foodorderingappcmp.features.orders.network.request.SubmitOrderRequest

interface OrderApiService {

    suspend fun addDeliveryAddressAndPayment(cardNumber: String,
                                             expireDate: String,
                                             cvv: String,
                                             nameOnCard: String,
                                             deliveryAddress: String): FoodOrderingResult<DeliveryAddressAndPaymentVO, FoodOrderingError>


    suspend fun getDeliveryAddressesAndPaymentMethods(): FoodOrderingResult<DeliveryAddressAndPaymentListVO, FoodOrderingError>


    suspend fun submitOrder(submitOrderRequest: SubmitOrderRequest): FoodOrderingResult<OrderItemVO, FoodOrderingError>

    suspend fun getOrdersForUser(): FoodOrderingResult<List<OrderItemVO>?, FoodOrderingError>
}