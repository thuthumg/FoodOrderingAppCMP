package org.ttm.foodorderingappcmp.features.orders.data.repository

import kotlinx.coroutines.flow.Flow
import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.core.persistence.AppDatabaseProvider
import org.ttm.foodorderingappcmp.features.orders.network.api_service.OrderApiService
import org.ttm.foodorderingappcmp.features.orders.network.impl.OrderApiServiceImpl
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentListVO
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

object CartRepository {

    val appDatabase = AppDatabaseProvider.appDatabase

    val orderApiService: OrderApiService = OrderApiServiceImpl

    suspend fun getAllCartFromDb(): Resource<List<FoodItemVO>> =

        try {

            val responseData = appDatabase.cartDao().getAllCart()

            Resource.Success(responseData)

        } catch (e: Exception) {
            Resource.Error(e.message  ?: "Something went wrong!")
        }


    fun getAllCartFromDbFlow(): Resource<Flow<List<FoodItemVO>?>> =

        try {
            val responseData = appDatabase.cartDao().getAllCartFromDbFlow()

            Resource.Success(responseData)

        } catch (e: Exception) {
            Resource.Error(e.message  ?: "Something went wrong!")
        }

    suspend fun insertCart(foodItemVO: FoodItemVO){
        appDatabase.cartDao().insertCart(foodItemVO)
    }

    suspend fun deleteCart(foodItemVO: FoodItemVO){
        appDatabase.cartDao().deleteCart(foodItemVO.id)
    }

    suspend fun deleteAllCart(){
        appDatabase.cartDao().deleteAllCart()
    }
    suspend fun getDeliveryAddressesAndPaymentMethods(): Resource<DeliveryAddressAndPaymentListVO> =
        try {

            val responseData = orderApiService.getDeliveryAddressesAndPaymentMethods()

            Resource.Success(responseData)

        } catch (e: Exception) {
            Resource.Error(e.message  ?: "Something went wrong!")
        }

}