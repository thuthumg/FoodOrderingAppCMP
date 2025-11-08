package org.ttm.foodorderingappcmp.features.orders.persistence.type_converter

import androidx.room.TypeConverter
import org.ttm.foodorderingappcmp.core.utils.universalJsonParser
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressVO
import org.ttm.foodorderingappcmp.features.orders.data.vos.PaymentVO

class PaymentMethodTypeConverter {

    @TypeConverter
    fun fromPaymentMethod(paymentVO: PaymentVO) : String{
        return paymentVO.let {
            universalJsonParser.encodeToString(it)
        }
    }

    @TypeConverter
    fun toPaymentMethod(jsonString: String): PaymentVO{
        return jsonString.let {
            universalJsonParser.decodeFromString(it)
        }
    }
}