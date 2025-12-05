package org.ttm.foodorderingappcmp.features.orders.network.impl

import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import org.ttm.foodorderingappcmp.core.network.FoodOrderingError
import org.ttm.foodorderingappcmp.core.network.FoodOrderingResult
import org.ttm.foodorderingappcmp.core.network.HttpClientProvider
import org.ttm.foodorderingappcmp.core.network.safeApiCall
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
    ): FoodOrderingResult<DeliveryAddressAndPaymentVO, FoodOrderingError> {

        return safeApiCall {
            HttpClientProvider.httpClient.post(DELIVERY_ADDRESS_AND_PAYMENT_METHOD){
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
        }
    }

    override suspend fun getDeliveryAddressesAndPaymentMethods(): FoodOrderingResult<DeliveryAddressAndPaymentListVO, FoodOrderingError> {

        return safeApiCall {
            HttpClientProvider.httpClient.get(
                GET_DELIVERY_ADDRESS_AND_PAYMENT_METHODS
            ){
                header(HttpHeaders.Authorization,"Bearer $apiToken")
            }
        }
    }

    override suspend fun submitOrder(submitOrderRequest: SubmitOrderRequest): FoodOrderingResult<OrderItemVO, FoodOrderingError> {

        return safeApiCall { HttpClientProvider.httpClient.post(SUBMIT_ORDER){
            header(HttpHeaders.Authorization,"Bearer $apiToken")
            setBody(submitOrderRequest)
        }
        }
    }

    override suspend fun getOrdersForUser(): FoodOrderingResult<List<OrderItemVO>?, FoodOrderingError> {

        return safeApiCall {
            HttpClientProvider.httpClient.get(GET_ORDERS_FOR_USER){
                header(HttpHeaders.Authorization,"Bearer $apiToken")
            }
        }
    }
}