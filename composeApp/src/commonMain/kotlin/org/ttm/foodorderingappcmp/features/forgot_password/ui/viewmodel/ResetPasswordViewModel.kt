package org.ttm.foodorderingappcmp.features.forgot_password.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.core.network.FoodOrderingErrorEnums
import org.ttm.foodorderingappcmp.core.utils.apiToken
import org.ttm.foodorderingappcmp.features.forgot_password.actions.ResetPasswordActions
import org.ttm.foodorderingappcmp.features.forgot_password.data.repository.ForgotPasswordRepository
import org.ttm.foodorderingappcmp.features.forgot_password.events.ResetPasswordEvents
import org.ttm.foodorderingappcmp.features.forgot_password.events.ResetPasswordEvents.NavigateToForgotPassword
import org.ttm.foodorderingappcmp.features.forgot_password.events.ResetPasswordEvents.NavigateToLogin
import org.ttm.foodorderingappcmp.features.forgot_password.ui.state.ResetPasswordState

class ResetPasswordViewModel(val email: String): ViewModel(){

val forgotPasswordRepository = ForgotPasswordRepository

private val _state = MutableStateFlow(ResetPasswordState())
val resetPasswordState = _state.asStateFlow()

    private val _navigationSharedFlow: MutableSharedFlow<ResetPasswordEvents> = MutableSharedFlow()

    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()


    fun forgotPassword(){


    val errorMessage = when {
        _state.value.password.isBlank() -> "Password is required."
        _state.value.confirmPassword.isBlank() -> "ConfirmPassword is required."
        (_state.value.password != _state.value.confirmPassword) -> "Passwords do not match."
        else -> null
    }

    if (errorMessage != null) {
        _state.update {
            it.copy(
                loading = false,
                message = errorMessage,
            )
        }
        return
    }



    viewModelScope.launch {
        _state.update { it.copy(loading = true,
            message = "") }

        forgotPasswordRepository.forgotPassword(
            email = email,
            password = _state.value.password,
            onSuccess = {
                _state.update {
                    it.copy(
                        loading = false,
                        message = "Password reset successful. Please log in with your new password.",
                        showSuccessDialog = true,
                        loginStatus = false
                    )
                }
            },
            onFailure = { message, type ->
                when(type){

                    FoodOrderingErrorEnums.Remote.UNAUTHORIZED -> {
                        _state.update {
                            it.copy(
                                loading = false,
                                message = message,
                                loginStatus = true,
                                showSuccessDialog = false
                            )
                        }
                    }
                    else -> {
                        _state.update {
                            it.copy(
                                loading = false,
                                message = message,
                                loginStatus = false,
                                showSuccessDialog = false
                            )
                        }
                    }
                }

            }
        )
    }
}


    fun onAction(resetPasswordActions: ResetPasswordActions){
        when(resetPasswordActions){
            is ResetPasswordActions.OnConfirmPasswordChanged -> {
                _state.update {
                    it.copy(
                        loading = false,
                        message = "",
                        loginStatus = false,
                        confirmPassword = resetPasswordActions.confirmPassword
                    )
                }
            }
            is ResetPasswordActions.OnPasswordChanged -> {
                _state.update {
                    it.copy(
                        loading = false,
                        message = "",
                        loginStatus = false,
                        password = resetPasswordActions.password
                    )
                }
            }
            is ResetPasswordActions.OnTapBack -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(NavigateToForgotPassword())
                }
            }
            is ResetPasswordActions.OnTapResetPassword -> {
                forgotPassword()

            }

            is ResetPasswordActions.OnErrorDialogDismissed -> {
                _state.update {
                    it.copy(
                        loading = false,
                        message = "",
                        loginStatus = false,
                        showSuccessDialog = false
                    )
                }
            }
            is ResetPasswordActions.OnUnauthorized -> {
                _state.update {
                    it.copy(
                        loading = false,
                        message = "",
                        loginStatus = true,
                        showSuccessDialog = false
                    )
                }
            }

            is ResetPasswordActions.OnTapOK -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(NavigateToLogin())
                }

            }
        }
    }

}