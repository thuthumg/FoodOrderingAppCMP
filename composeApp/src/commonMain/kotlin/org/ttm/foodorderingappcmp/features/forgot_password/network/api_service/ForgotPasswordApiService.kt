package org.ttm.foodorderingappcmp.features.forgot_password.network.api_service

import org.ttm.foodorderingappcmp.features.forgot_password.network.responses.CheckEmailResponse

interface ForgotPasswordApiService {

    suspend fun forgotPasswordCheckEmail(email: String): CheckEmailResponse

    suspend fun forgotPassword(email: String, password: String)

}