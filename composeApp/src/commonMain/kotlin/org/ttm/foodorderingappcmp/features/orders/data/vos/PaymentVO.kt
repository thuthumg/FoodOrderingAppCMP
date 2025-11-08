package org.ttm.foodorderingappcmp.features.orders.data.vos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentVO(

    @SerialName("id")
    val id: Long? = null,

    @SerialName("card_number")
    val cardNumber: String,

    @SerialName("expiry_date")
    val expiryDate: String,

    @SerialName("cvv")
    val cvv: Int,

    @SerialName("name_on_card")
    val nameOnCard: String,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null

){
    fun formatCardNumber(): String {
        val digits = cardNumber.filter { it.isDigit() }
        return digits.chunked(4).joinToString(" ")
    }
}
