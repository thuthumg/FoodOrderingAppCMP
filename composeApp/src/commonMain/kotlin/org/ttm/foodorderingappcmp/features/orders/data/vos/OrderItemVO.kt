package org.ttm.foodorderingappcmp.features.orders.data.vos

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.ttm.foodorderingappcmp.features.restaurants.data.vos.FoodItemVO

@Serializable
data class OrderItemVO(

    @SerialName("id")
    val id: Long,

    @SerialName("order_number")
    val orderNumber: String,

    @SerialName("total_cost")
    val totalCost: Double,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("delivery_address")
    val deliveryAddress: DeliveryAddressVO,

    @SerialName("payment_method")
    val paymentMethod: PaymentVO,

    @SerialName("food_items")
    val foodItems: List<FoodItemVO>

){
    @OptIn(kotlin.time.ExperimentalTime::class)
    fun orderDisplayDate(): String {
        // Example input: "2025-11-03T13:21:56.68302Z"
        val instant = Instant.parse(createdAt)
        val systemTimeZone = TimeZone.currentSystemDefault()
        val localDateTime = instant.toLocalDateTime(systemTimeZone)

        fun String.capFirst(): String =
            lowercase().replaceFirstChar { it.titlecase() }

        val dayOfWeekName = localDateTime.dayOfWeek.name.capFirst()   // "Monday"
        val monthShort = localDateTime.month.name.capFirst().take(3)  // "Nov"
        val dayOfMonth = localDateTime.dayOfMonth                     // 3, 9, etc.

        return "$dayOfWeekName $monthShort $dayOfMonth"               // e.g. "Monday Nov 3"
    }
}
