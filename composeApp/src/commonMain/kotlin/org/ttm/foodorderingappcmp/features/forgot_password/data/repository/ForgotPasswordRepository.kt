package org.ttm.foodorderingappcmp.features.forgot_password.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.ttm.foodorderingappcmp.core.network.FoodOrderingErrorEnums
import org.ttm.foodorderingappcmp.core.network.FoodOrderingResult
import org.ttm.foodorderingappcmp.core.network.onError
import org.ttm.foodorderingappcmp.core.network.onSuccess
import org.ttm.foodorderingappcmp.features.forgot_password.network.api_service.ForgotPasswordApiService
import org.ttm.foodorderingappcmp.features.forgot_password.network.impl.ForgotPasswordApiServiceImpl
import org.ttm.foodorderingappcmp.features.forgot_password.network.responses.CheckEmailResponse


object ForgotPasswordRepository {
    val forgotPasswordApiService: ForgotPasswordApiService = ForgotPasswordApiServiceImpl

    suspend fun forgotPasswordCheckEmail(email: String,
                                         onSuccess: (CheckEmailResponse) -> Unit,
                                         onFailure: (String, FoodOrderingErrorEnums?) -> Unit) =
        withContext(Dispatchers.IO){
            forgotPasswordApiService.forgotPasswordCheckEmail(email)
                .onSuccess { checkEmailResponse ->
                    onSuccess(checkEmailResponse)
                }
                .onError { error ->
                    onFailure(error.error,error.errorType)
                }
        }

    suspend fun forgotPassword(email: String, password: String,
                               onSuccess: (String) -> Unit,
                               onFailure: (String, FoodOrderingErrorEnums?) -> Unit)=
        withContext(Dispatchers.IO){
            forgotPasswordApiService.forgotPassword(email,password)
                .onSuccess {
                    onSuccess("")
                }
                .onError {  error ->
                    onFailure(error.error,error.errorType)
                }

        }

}