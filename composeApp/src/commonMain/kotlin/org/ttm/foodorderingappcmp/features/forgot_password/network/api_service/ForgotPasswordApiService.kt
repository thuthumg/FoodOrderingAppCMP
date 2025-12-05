package org.ttm.foodorderingappcmp.features.forgot_password.network.api_service

import org.ttm.foodorderingappcmp.core.network.FoodOrderingError
import org.ttm.foodorderingappcmp.core.network.FoodOrderingResult
import org.ttm.foodorderingappcmp.features.forgot_password.network.responses.CheckEmailResponse
import org.ttm.foodorderingappcmp.features.orders.data.vos.OrderItemVO

interface ForgotPasswordApiService {

    suspend fun forgotPasswordCheckEmail(email: String): FoodOrderingResult<CheckEmailResponse, FoodOrderingError>


    suspend fun forgotPassword(email: String, password: String): FoodOrderingResult<Unit, FoodOrderingError>

}