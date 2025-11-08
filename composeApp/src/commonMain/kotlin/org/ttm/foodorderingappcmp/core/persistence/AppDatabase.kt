package org.ttm.foodorderingappcmp.core.persistence

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import org.ttm.foodorderingappcmp.auth.data.vos.LoginRegisterVO
import org.ttm.foodorderingappcmp.auth.persistence.daos.UserDao
import org.ttm.foodorderingappcmp.features.orders.data.vos.DeliveryAddressAndPaymentVO
import org.ttm.foodorderingappcmp.features.orders.persistence.daos.DeliveryAddressAndPaymentDao
import org.ttm.foodorderingappcmp.features.orders.persistence.type_converter.DeliveryAddressTypeConverter
import org.ttm.foodorderingappcmp.features.orders.persistence.type_converter.PaymentMethodTypeConverter
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO
import org.ttm.foodorderingappcmp.features.restaurants.persistence.daos.CartDao

@Database(
    entities = [
        LoginRegisterVO::class,
        FoodItemVO::class,
        DeliveryAddressAndPaymentVO::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(
    DeliveryAddressTypeConverter::class,
    PaymentMethodTypeConverter::class
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun cartDao() : CartDao

    abstract fun deliveryAddressAndPaymentDao() : DeliveryAddressAndPaymentDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor: RoomDatabaseConstructor<AppDatabase>{
    override fun initialize(): AppDatabase

}