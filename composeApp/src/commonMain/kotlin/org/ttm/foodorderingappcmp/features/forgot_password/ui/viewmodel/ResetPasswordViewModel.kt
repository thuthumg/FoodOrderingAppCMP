package org.ttm.foodorderingappcmp.features.forgot_password.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.features.forgot_password.data.repository.ForgotPasswordRepository
import org.ttm.foodorderingappcmp.features.forgot_password.ui.state.ResetPasswordState

class ResetPasswordViewModel(val email: String): ViewModel(){

val forgotPasswordRepository = ForgotPasswordRepository

private val _state = MutableStateFlow(ResetPasswordState())
val resetPasswordState = _state.asStateFlow()


fun forgotPassword(password: String,confirmPassword: String){


    val errorMessage = when {
        password.isBlank() -> "Password is required."
        confirmPassword.isBlank() -> "ConfirmPassword is required."
        (password != confirmPassword) -> "Passwords do not match."
        else -> null
    }

    if (errorMessage != null) {
        _state.update {
            it.copy(
                loading = false,
                message = errorMessage,
                errorDialogShowStatus = true,
            )
        }
        return
    }



    viewModelScope.launch {
        _state.update { it.copy(loading = true, errorDialogShowStatus = false, message = "") }

            when(val result = forgotPasswordRepository.forgotPassword(email = email, password = password)){
                is Resource.Error -> _state.update {
                    it.copy(
                        loading = false,
                        message = result.message,
                        resetPasswordStatus = false,
                        errorDialogShowStatus = true
                    )
                }
                is Resource.Success -> _state.update {
                    it.copy(
                        loading = false,
                        message = "Password reset successful. Please log in with your new password.",
                        resetPasswordStatus = true,
                        errorDialogShowStatus = false
                    )
                }
            }





    }
}


fun onDismissErrorAlertDialog() {
    _state.update {
        it.copy(loading = false, errorDialogShowStatus = false, message = "")
    }
}

fun onDismissSuccessAlertDialog() {
        _state.update {
            it.copy(loading = false,
                errorDialogShowStatus = false,
                resetPasswordStatus = false,
                goToLoginStatus = true,
                message = "")
        }
    }

}