package org.ttm.foodorderingappcmp.features.orders.network.impl

import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import org.ttm.foodorderingappcmp.core.network.HttpClientProvider
import org.ttm.foodorderingappcmp.core.network.transformResult
import org.ttm.foodorderingappcmp.core.utils.DELIVERY_ADDRESS_AND_PAYMENT_METHOD
import org.ttm.foodorderingappcmp.core.utils.GET_DELIVERY_ADDRESS_AND_PAYMENT_METHODS
import org.ttm.foodorderingappcmp.core.utils.GET_ORDERS_FOR_USER
import org.ttm.foodorderingappcmp.core.utils.SUBMIT_ORDER
import org.ttm.foodorderingappcmp.core.utils.apiToken
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressVO
import org.ttm.foodorderingappcmp.features.orders.data.vos.PaymentVO
import org.ttm.foodorderingappcmp.features.orders.network.api_service.OrderApiService
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentListVO
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentVO
import org.ttm.foodorderingappcmp.features.orders.network.request.SubmitOrderRequest
import org.ttm.foodorderingappcmp.features.orders.data.vos.OrderItemVO

object OrderApiServiceImpl: OrderApiService {

    override suspend fun addDeliveryAddressAndPayment(
        cardNumber: String,
        expireDate: String,
        cvv: String,
        nameOnCard: String,
        deliveryAddress: String,
    ): DeliveryAddressAndPaymentVO {
        val httpResponse = HttpClientProvider.httpClient.post(DELIVERY_ADDRESS_AND_PAYMENT_METHOD){
            header(HttpHeaders.Authorization,"Bearer $apiToken")
            setBody(
                DeliveryAddressAndPaymentVO(
                    paymentMethod = PaymentVO(
                        cardNumber = cardNumber,
                        expiryDate = expireDate,
                        cvv = cvv.toInt(),
                        nameOnCard = nameOnCard
                    ),
                    deliveryAddress = DeliveryAddressVO(
                        streetAddress = deliveryAddress
                    )
                )
            )
        }

        return transformResult<DeliveryAddressAndPaymentVO>(httpResponse)
    }

    override suspend fun getDeliveryAddressesAndPaymentMethods(): DeliveryAddressAndPaymentListVO {
        val httpResponse = HttpClientProvider.httpClient.get(
            GET_DELIVERY_ADDRESS_AND_PAYMENT_METHODS
        ){
            header(HttpHeaders.Authorization,"Bearer $apiToken")
        }

        return transformResult<DeliveryAddressAndPaymentListVO>(httpResponse)
    }

    override suspend fun submitOrder(submitOrderRequest: SubmitOrderRequest): OrderItemVO {
        val httpResponse = HttpClientProvider.httpClient.post(SUBMIT_ORDER){
            header(HttpHeaders.Authorization,"Bearer $apiToken")
            setBody(submitOrderRequest)
        }

        return transformResult<OrderItemVO>(httpResponse)
    }

    override suspend fun getOrdersForUser(): List<OrderItemVO>? {
       val httpResponse = HttpClientProvider.httpClient.get(GET_ORDERS_FOR_USER){
           header(HttpHeaders.Authorization,"Bearer $apiToken")
       }

        return transformResult<List<OrderItemVO>?>(httpResponse)
    }
}