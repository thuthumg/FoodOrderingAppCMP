package org.ttm.foodorderingappcmp.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodOrderingError(
    var errorType: FoodOrderingErrorEnums? = null,
    @SerialName("error")
    val error: String
)