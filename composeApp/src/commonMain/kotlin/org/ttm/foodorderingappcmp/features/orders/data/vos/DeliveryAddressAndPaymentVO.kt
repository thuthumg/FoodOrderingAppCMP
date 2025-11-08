package org.ttm.foodorderingappcmp.features.orders.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity("delivery_address_and_payment")
@Serializable
data class DeliveryAddressAndPaymentVO(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,


    @ColumnInfo("payment_method")
    @SerialName("payment_method")
    val paymentMethod: PaymentVO,

    @ColumnInfo("delivery_address")
    @SerialName("delivery_address")
    val deliveryAddress: DeliveryAddressVO
)