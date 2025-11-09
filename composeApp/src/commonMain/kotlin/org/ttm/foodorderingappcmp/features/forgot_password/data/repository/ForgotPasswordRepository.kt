package org.ttm.foodorderingappcmp.features.forgot_password.data.repository

import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.features.forgot_password.network.api_service.ForgotPasswordApiService
import org.ttm.foodorderingappcmp.features.forgot_password.network.impl.ForgotPasswordApiServiceImpl
import org.ttm.foodorderingappcmp.features.forgot_password.network.responses.CheckEmailResponse


object ForgotPasswordRepository {
    val forgotPasswordApiService: ForgotPasswordApiService = ForgotPasswordApiServiceImpl

    suspend fun forgotPasswordCheckEmail(email: String): Resource<CheckEmailResponse> =
        try {

            val responseData = forgotPasswordApiService.forgotPasswordCheckEmail(email)

            Resource.Success(responseData)

        }catch (e: Exception){
            Resource.Error(e.message ?: "Something went wrong!")
        }

    suspend fun forgotPassword(email: String, password: String): Resource<String> =
        try {

           forgotPasswordApiService.forgotPassword(email,password)

            Resource.Success("")

        }catch (e: Exception){
            Resource.Error(e.message ?: "Something went wrong!")
        }
}