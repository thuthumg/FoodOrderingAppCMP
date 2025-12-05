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
import org.ttm.foodorderingappcmp.core.utils.emailRegex
import org.ttm.foodorderingappcmp.features.forgot_password.actions.ForgotPasswordActions
import org.ttm.foodorderingappcmp.features.forgot_password.data.repository.ForgotPasswordRepository
import org.ttm.foodorderingappcmp.features.forgot_password.events.ForgotPasswordEvents
import org.ttm.foodorderingappcmp.features.forgot_password.events.ForgotPasswordEvents.*
import org.ttm.foodorderingappcmp.features.forgot_password.ui.state.ForgotPasswordState

class ForgotPasswordViewModel: ViewModel() {

    val forgotPasswordRepository = ForgotPasswordRepository

    private val _state = MutableStateFlow(ForgotPasswordState())
    val forgotPasswordState = _state.asStateFlow()

    private val _navigationSharedFlow: MutableSharedFlow<ForgotPasswordEvents> = MutableSharedFlow()

    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()

    fun checkEmail(){

        val errorMessage = when {
            _state.value.email.isBlank() -> "Email is required."
            ! _state.value.email.matches(emailRegex) -> "Invalid email format."
            else -> null
        }

        if (errorMessage != null) {
            _state.update {
                it.copy(
                    loading = false,
                    message = errorMessage,
                   // errorDialogShowStatus = true,
                )
            }
            return
        }




        viewModelScope.launch {
            _state.update { it.copy(
                loading = true,
                //errorDialogShowStatus = false,
                message = "") }

            forgotPasswordRepository.forgotPasswordCheckEmail(email =  _state.value.email,
                onSuccess = { checkEmailResponse ->

                    _state.update {
                        it.copy(
                            checkEmailResponse = checkEmailResponse,
                            loading = false,
                            message = "",
                            //errorDialogShowStatus = false
                        )
                    }
                    onContinueHandled(checkEmailResponse.user.email)
                },
                onFailure = { message,type ->

                    when(type){

                        FoodOrderingErrorEnums.Remote.UNAUTHORIZED -> {
                            _state.update {
                                it.copy(
                                    loading = false,
                                    message = message,
                                    loginStatus = true
                                )
                            }
                        }
                        else -> {
                            _state.update {
                                it.copy(
                                    loading = false,
                                    message = message,
                                    loginStatus = false
                                )
                            }
                        }
                    }




                })
        }
    }

    fun onContinueHandled(data: String) {
        viewModelScope.launch {
            _navigationSharedFlow.emit(NavigateToResetPassword(data))
        }
    }
//
//    fun onDismissErrorAlertDialog() {
//        _state.update {
//            it.copy(loading = false,
//                message = "")
//        }
//    }
//
//    fun onTapContinueHandled() {
//        _state.update { it.copy(checkEmailResponse = null) }
//    }


    fun onAction(action: ForgotPasswordActions){
        when(action){
            is ForgotPasswordActions.OnEmailChanged -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            loading = false,
                            message = "",
                            email = action.email
                        )
                    }
                }
            }
            is ForgotPasswordActions.OnTapBack -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(NavigateToHome())
                }

            }
            is ForgotPasswordActions.OnTapContinue -> {
                checkEmail()
            }

            is ForgotPasswordActions.OnErrorDialogDismissed -> {
                _state.update {
                    it.copy(
                        loading = false,
                        message = ""
                    )
                }
            }
            is ForgotPasswordActions.OnUnauthorized -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(NavigateToLogin())
                }
            }
        }
    }


}