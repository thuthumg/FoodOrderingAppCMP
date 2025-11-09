package org.ttm.foodorderingappcmp.features.forgot_password.network.impl

import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import org.ttm.foodorderingappcmp.core.network.HttpClientProvider
import org.ttm.foodorderingappcmp.core.network.transformResult
import org.ttm.foodorderingappcmp.core.utils.FORGET_PASSWORD
import org.ttm.foodorderingappcmp.core.utils.FORGET_PASSWORD_CHECK_EMAIL
import org.ttm.foodorderingappcmp.core.utils.apiToken
import org.ttm.foodorderingappcmp.features.forgot_password.network.api_service.ForgotPasswordApiService
import org.ttm.foodorderingappcmp.features.forgot_password.network.responses.CheckEmailResponse
import org.ttm.foodorderingappcmp.features.forgot_password.request.CheckEmailRequest
import org.ttm.foodorderingappcmp.features.forgot_password.request.ForgotPasswordRequest

object ForgotPasswordApiServiceImpl: ForgotPasswordApiService
{
    override suspend fun forgotPasswordCheckEmail(email: String): CheckEmailResponse {
        val httpResponse = HttpClientProvider.httpClient.post(FORGET_PASSWORD_CHECK_EMAIL){
            setBody(CheckEmailRequest(email = email))
        }

        return transformResult<CheckEmailResponse>(httpResponse)
    }

    override suspend fun forgotPassword(
        email: String,
        password: String,
    ) {
        val httpResponse = HttpClientProvider.httpClient.post(FORGET_PASSWORD){
            header(HttpHeaders.Authorization, "Bearer $apiToken")
            setBody(ForgotPasswordRequest(email = email, password = password))
        }

        return transformResult<Unit>(httpResponse)
    }
}