package org.ttm.foodorderingappcmp.features.orders.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.core.persistence.AppDatabaseProvider
import org.ttm.foodorderingappcmp.features.orders.network.api_service.OrderApiService
import org.ttm.foodorderingappcmp.features.orders.network.impl.OrderApiServiceImpl
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentVO

object CheckoutRepository {

    val orderApiService: OrderApiService = OrderApiServiceImpl
    val appDatabase = AppDatabaseProvider.appDatabase

    suspend fun addDeliveryAddressAndPayment(
        cardNumber: String,
        expireDate: String,
        cvv: String,
        nameOnCard: String,
        deliveryAddress: String
    ): Resource<DeliveryAddressAndPaymentVO> =
        withContext(Dispatchers.IO) {
            try {
                val responseData = orderApiService.addDeliveryAddressAndPayment(
                    cardNumber = cardNumber,
                    expireDate = expireDate,
                    cvv = cvv,
                    nameOnCard = nameOnCard,
                    deliveryAddress = deliveryAddress
                )

                Resource.Success(responseData)

            } catch (e: Exception) {
                Resource.Error(e.message ?: "Something went wrong!")
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