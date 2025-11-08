package org.ttm.foodorderingappcmp.features.orders.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentVO

@Dao
interface DeliveryAddressAndPaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeliveryAddressAndPayment(deliveryAddressAndPaymentVO: DeliveryAddressAndPaymentVO)

    @Query("DELETE FROM delivery_address_and_payment")
    suspend fun deleteAllDeliveryPayment()

    @Query("SELECT * FROM delivery_address_and_payment")
    suspend fun getAllDeliveryAddressAndPayment() : DeliveryAddressAndPaymentVO
}