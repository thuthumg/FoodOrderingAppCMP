package org.ttm.foodorderingappcmp.features.orders.persistence.type_converter

import androidx.room.TypeConverter
import org.ttm.foodorderingappcmp.core.utils.universalJsonParser
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressVO

class DeliveryAddressTypeConverter {

    @TypeConverter
    fun fromDeliveryAddress(deliveryAddressVO: DeliveryAddressVO) : String{
        return deliveryAddressVO.let {
            universalJsonParser.encodeToString(it)
        }
    }

    @TypeConverter
    fun toDeliveryAddress(jsonString: String): DeliveryAddressVO{
        return jsonString.let {
            universalJsonParser.decodeFromString(it)
        }
    }
}