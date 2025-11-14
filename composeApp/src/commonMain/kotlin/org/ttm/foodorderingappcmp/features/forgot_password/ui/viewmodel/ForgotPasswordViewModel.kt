package org.ttm.foodorderingappcmp.features.forgot_password.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.core.utils.emailRegex
import org.ttm.foodorderingappcmp.features.forgot_password.data.repository.ForgotPasswordRepository
import org.ttm.foodorderingappcmp.features.forgot_password.network.responses.CheckEmailResponse
import org.ttm.foodorderingappcmp.features.forgot_password.ui.state.ForgotPasswordState

class ForgotPasswordViewModel: ViewModel() {

    val forgotPasswordRepository = ForgotPasswordRepository

    private val _state = MutableStateFlow(ForgotPasswordState())
    val forgotPasswordState = _state.asStateFlow()

    private val _onNavigateToResetPassword = MutableSharedFlow<CheckEmailResponse>()

    val onNavigateToResetPassword = _onNavigateToResetPassword.asSharedFlow()

    fun checkEmail(email: String){

        val errorMessage = when {
            email.isBlank() -> "Email is required."
            !email.matches(emailRegex) -> "Invalid email format."
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

            when(val result = forgotPasswordRepository.forgotPasswordCheckEmail(email = email)){
                is Resource.Error -> _state.update {
                    it.copy(
                        loading = false,
                        message = result.message,
                        errorDialogShowStatus = true
                    )
                }
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            checkEmailResponse = result.data,
                            loading = false,
                            message = "",
                            errorDialogShowStatus = false
                        )
                    }
                    onContinueHandled(result.data)
                }
            }

        }
    }

    fun onContinueHandled(data: CheckEmailResponse) {
        viewModelScope.launch {
            _onNavigateToResetPassword.emit(data)
        }
    }

    fun onDismissErrorAlertDialog() {
        _state.update {
            it.copy(loading = false, errorDialogShowStatus = false, message = "")
        }
    }

    fun onTapContinueHandled() {
        _state.update { it.copy(checkEmailResponse = null) }
    }
}