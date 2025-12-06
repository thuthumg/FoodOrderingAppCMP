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

object CheckoutRepository {

    val orderApiService: OrderApiService = OrderApiServiceImpl
    val appDatabase = AppDatabaseProvider.appDatabase

    suspend fun addDeliveryAddressAndPayment(
        cardNumber: String,
        expireDate: String,
        cvv: String,
        nameOnCard: String,
        deliveryAddress: String,
        onSuccess: (DeliveryAddressAndPaymentVO) -> Unit,
        onFailure: (String, FoodOrderingErrorEnums?) -> Unit
    ) =
        withContext(Dispatchers.IO) {

               orderApiService.addDeliveryAddressAndPayment(
                    cardNumber = cardNumber,
                    expireDate = expireDate,
                    cvv = cvv,
                    nameOnCard = nameOnCard,
                    deliveryAddress = deliveryAddress
                ).onSuccess {
                    onSuccess(it)
               }.onError { error ->
                   onFailure(error.error,error.errorType)

               }


        }



    suspend fun insertDeliveryAddressAndPayment(deliveryAddressAndPaymentVO: DeliveryAddressAndPaymentVO){
        appDatabase.deliveryAddressAndPaymentDao().deleteAllDeliveryPayment()
        appDatabase.deliveryAddressAndPaymentDao().insertDeliveryAddressAndPayment(deliveryAddressAndPaymentVO)
    }

    suspend fun deleteAllDeliveryAddressAndPayment(){
        appDatabase.deliveryAddressAndPaymentDao().deleteAllDeliveryPayment()
    }
}