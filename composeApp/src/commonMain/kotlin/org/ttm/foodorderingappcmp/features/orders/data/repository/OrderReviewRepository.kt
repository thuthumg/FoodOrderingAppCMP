package org.ttm.foodorderingappcmp.features.orders.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.ttm.foodorderingappcmp.core.network.FoodOrderingErrorEnums
import org.ttm.foodorderingappcmp.core.network.onError
import org.ttm.foodorderingappcmp.core.network.onSuccess
import org.ttm.foodorderingappcmp.core.persistence.AppDatabaseProvider
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentVO
import org.ttm.foodorderingappcmp.features.orders.network.api_service.OrderApiService
import org.ttm.foodorderingappcmp.features.orders.network.impl.OrderApiServiceImpl
import org.ttm.foodorderingappcmp.features.orders.network.request.SubmitOrderRequest
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

object OrderReviewRepository {

    val orderApiService: OrderApiService = OrderApiServiceImpl
    val appDatabase = AppDatabaseProvider.appDatabase

    suspend fun getDeliveryAddressAndPaymentFromDb(): DeliveryAddressAndPaymentVO {
       return appDatabase.deliveryAddressAndPaymentDao().getAllDeliveryAddressAndPayment()
    }

    suspend fun submitOrder(
        paymentId: Long,
        deliveryAddressId: Long,
        foodItemList : List<FoodItemVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String, FoodOrderingErrorEnums?) -> Unit
    ) = withContext(Dispatchers.IO){
        orderApiService.submitOrder(
            SubmitOrderRequest(
                paymentMethodId = paymentId,
                deliveryAddressId = deliveryAddressId,
                foodItems = foodItemList,
            )
        ).onSuccess {
            onSuccess("Order submitted successfully.")
        }.onError { error ->
            onFailure(error.error,error.errorType)
        }
    }

}