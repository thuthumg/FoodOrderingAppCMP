package org.ttm.foodorderingappcmp.features.orders.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.features.orders.network.api_service.OrderApiService
import org.ttm.foodorderingappcmp.features.orders.network.impl.OrderApiServiceImpl
import org.ttm.foodorderingappcmp.features.orders.data.vos.OrderItemVO

object OrderListRepository {


    val orderApiService: OrderApiService = OrderApiServiceImpl

    suspend fun getOrdersForUser(): Resource<List<OrderItemVO>?> =

        withContext(Dispatchers.IO) {
            try {
                // Call API
                val responseData = orderApiService.getOrdersForUser()
                Resource.Success(responseData)
            } catch (e: Exception) {
                Resource.Error(e.message ?:"Something went wrong!")
            }
        }

}