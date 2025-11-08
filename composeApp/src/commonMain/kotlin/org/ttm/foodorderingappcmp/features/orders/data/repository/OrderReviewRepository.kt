package org.ttm.foodorderingappcmp.features.orders.data.repository

import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.core.persistence.AppDatabaseProvider
import org.ttm.foodorderingappcmp.features.orders.network.api_service.OrderApiService
import org.ttm.foodorderingappcmp.features.orders.network.impl.OrderApiServiceImpl
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentVO
import org.ttm.foodorderingappcmp.features.orders.network.request.SubmitOrderRequest
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

object OrderReviewRepository {

    val orderApiService: OrderApiService = OrderApiServiceImpl
    val appDatabase = AppDatabaseProvider.appDatabase

    suspend fun getDeliveryAddressAndPaymentFromDb(): Resource<DeliveryAddressAndPaymentVO> =

        try {

            val responseData = appDatabase.deliveryAddressAndPaymentDao().getAllDeliveryAddressAndPayment()

            Resource.Success(responseData)

        } catch (e: Exception) {
            Resource.Error(e.message  ?: "Something went wrong!")
        }


    suspend fun submitOrder(
        paymentId: Long,
        deliveryAddressId: Long,
        foodItemList : List<FoodItemVO>
    ): Resource<String> =
        try {

            orderApiService.submitOrder(SubmitOrderRequest(
                paymentMethodId = paymentId,
                deliveryAddressId = deliveryAddressId,
                foodItems = foodItemList,
            ))

            Resource.Success("Order submitted successfully.")

        }catch (e: Exception){
            Resource.Error(e.message ?: "Something went wrong!")
        }
}