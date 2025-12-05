package org.ttm.foodorderingappcmp.features.orders.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.ttm.foodorderingappcmp.core.network.FoodOrderingErrorEnums
import org.ttm.foodorderingappcmp.core.network.FoodOrderingResult
import org.ttm.foodorderingappcmp.core.network.onError
import org.ttm.foodorderingappcmp.core.network.onSuccess
import org.ttm.foodorderingappcmp.features.orders.network.api_service.OrderApiService
import org.ttm.foodorderingappcmp.features.orders.network.impl.OrderApiServiceImpl
import org.ttm.foodorderingappcmp.features.orders.data.vos.OrderItemVO

object OrderListRepository {


    val orderApiService: OrderApiService = OrderApiServiceImpl

    suspend fun getOrdersForUser(
        onSuccess: (List<OrderItemVO>?) -> Unit,
        onFailure: (String, FoodOrderingErrorEnums?) -> Unit
    ) =

        withContext(Dispatchers.IO) {

            orderApiService.getOrdersForUser()
                .onSuccess {
                    onSuccess(it)
                }.onError { error ->
                    onFailure(error.error,error.errorType)
                }


        }

}