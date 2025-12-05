package org.ttm.foodorderingappcmp.auth.network.api_service

import org.ttm.foodorderingappcmp.auth.network.responses.LoginRegisterResponse
import org.ttm.foodorderingappcmp.core.network.FoodOrderingError
import org.ttm.foodorderingappcmp.core.network.FoodOrderingResult

interface LoginRegisterService {

    suspend fun login(email: String, password: String) : FoodOrderingResult<LoginRegisterResponse, FoodOrderingError>

    suspend fun register(email: String, fullName: String, password: String) : FoodOrderingResult<LoginRegisterResponse, FoodOrderingError>
}