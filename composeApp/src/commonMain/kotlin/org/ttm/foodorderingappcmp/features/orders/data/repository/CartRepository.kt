package org.ttm.foodorderingappcmp.features.orders.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.ttm.foodorderingappcmp.core.network.FoodOrderingErrorEnums
import org.ttm.foodorderingappcmp.core.network.onError
import org.ttm.foodorderingappcmp.core.network.onSuccess
import org.ttm.foodorderingappcmp.core.persistence.AppDatabaseProvider
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentListVO
import org.ttm.foodorderingappcmp.features.orders.network.api_service.OrderApiService
import org.ttm.foodorderingappcmp.features.orders.network.impl.OrderApiServiceImpl
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

object CartRepository {

    val appDatabase = AppDatabaseProvider.appDatabase

    val orderApiService: OrderApiService = OrderApiServiceImpl

    suspend fun getAllCartFromDb(): List<FoodItemVO> =
        appDatabase.cartDao().getAllCart()


    fun getAllCartFromDbFlow(): Flow<List<FoodItemVO>?> =

        appDatabase.cartDao().getAllCartFromDbFlow()


    suspend fun insertCart(foodItemVO: FoodItemVO){
        appDatabase.cartDao().insertCart(foodItemVO)
    }

    suspend fun deleteCart(foodItemVO: FoodItemVO){
        appDatabase.cartDao().deleteCart(foodItemVO.id)
    }

    suspend fun deleteAllCart(){
        appDatabase.cartDao().deleteAllCart()
    }
    suspend fun getDeliveryAddressesAndPaymentMethods(
        onSuccess: (DeliveryAddressAndPaymentListVO) -> Unit,
        onFailure: (String, FoodOrderingErrorEnums?) -> Unit
    ) = withContext(Dispatchers.IO){
        orderApiService.getDeliveryAddressesAndPaymentMethods()
            .onSuccess { deliveryAddressAndPaymentListVO ->
                onSuccess(deliveryAddressAndPaymentListVO)
            }
            .onError { error ->
                onFailure(error.error,error.errorType)
            }
    }


}