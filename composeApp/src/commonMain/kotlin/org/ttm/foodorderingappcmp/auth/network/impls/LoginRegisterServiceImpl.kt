package org.ttm.foodorderingappcmp.auth.network.impls

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.ttm.foodorderingappcmp.auth.network.api_service.LoginRegisterService
import org.ttm.foodorderingappcmp.auth.network.requests.LoginRequest
import org.ttm.foodorderingappcmp.auth.network.requests.RegisterRequest
import org.ttm.foodorderingappcmp.auth.network.responses.LoginRegisterResponse
import org.ttm.foodorderingappcmp.core.network.FoodOrderingError
import org.ttm.foodorderingappcmp.core.network.FoodOrderingResult
import org.ttm.foodorderingappcmp.core.network.HttpClientProvider
import org.ttm.foodorderingappcmp.core.network.safeApiCall
import org.ttm.foodorderingappcmp.core.network.transformResult
import org.ttm.foodorderingappcmp.core.utils.LOGIN
import org.ttm.foodorderingappcmp.core.utils.REGISTER

object LoginRegisterServiceImpl: LoginRegisterService {
    override suspend fun login(
        email: String,
        password: String,
    ): FoodOrderingResult<LoginRegisterResponse, FoodOrderingError> {
        return safeApiCall{
            HttpClientProvider.httpClient.post(LOGIN){
                setBody(LoginRequest(email = email, password = password))
            }
        }
       // return transformResult<LoginRegisterResponse?>(httpResponse)

    }

    override suspend fun register(
        email: String,
        fullName: String,
        password: String,
    ): FoodOrderingResult<LoginRegisterResponse, FoodOrderingError> {

        return safeApiCall {
            HttpClientProvider.httpClient.post(REGISTER){
                setBody(RegisterRequest(email = email, fullName = fullName,password = password))
            }
        }
    }

}