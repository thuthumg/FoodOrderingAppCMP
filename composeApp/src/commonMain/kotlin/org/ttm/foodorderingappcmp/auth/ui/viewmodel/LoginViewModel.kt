package org.ttm.foodorderingappcmp.auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.auth.actions.LoginActions
import org.ttm.foodorderingappcmp.auth.data.repository.LoginRegisterRepository
import org.ttm.foodorderingappcmp.auth.events.LoginEvents
import org.ttm.foodorderingappcmp.auth.events.LoginEvents.NavigateToForgotPassword
import org.ttm.foodorderingappcmp.auth.events.LoginEvents.NavigateToHome
import org.ttm.foodorderingappcmp.auth.events.LoginEvents.NavigateToSignUp
import org.ttm.foodorderingappcmp.auth.ui.state.LoginState
import org.ttm.foodorderingappcmp.core.utils.apiToken
import org.ttm.foodorderingappcmp.core.utils.emailRegex

class LoginViewModel : ViewModel() {

    val loginRegisterRepo = LoginRegisterRepository
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    val passwordLength = 6


    private val _navigationSharedFlow : MutableSharedFlow<LoginEvents> = MutableSharedFlow()

    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()


    fun onClickLogin() {

        val errorMessage = when {
            _state.value.email.isBlank() -> "Email is required."
            !_state.value.email.matches(emailRegex) -> "Invalid email format."
            _state.value.password.isBlank() -> "Password is required."
            _state.value.password.length < passwordLength -> "Password must be at least 6 characters."
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

            loginRegisterRepo.login(
                email = _state.value.email,
                password = _state.value.password,
                onSuccess = { user ->
                    // update UI state
                    _state.update {
                        it.copy(
                            loading = false,
                            message = "",
                        )

                    }

                    apiToken = user.accessToken ?: ""
                    launch{
                        _navigationSharedFlow.emit(NavigateToHome())
                    }

                },
                onFailure = { message, type ->
                    _state.update {
                        it.copy(
                            loading = false,
                            message = message,
                        )
                    }
                }
            )
        }


    }

    fun clearUserData(){
        viewModelScope.launch {
            loginRegisterRepo.deleteAllUserData()
            _state.update {
                it.copy(
                    loading = false,
                    message ="")
            }
        }

    }

    fun onAction(action: LoginActions){
        when(action){
            is LoginActions.OnTapLoginAction -> {
                onClickLogin()
            }

            is LoginActions.OnEmailChanged -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            loading = false,
                            message ="",
                            email = action.email
                        )

                    }

                }
            }
            is LoginActions.OnTapForgotPasswordAction -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(NavigateToForgotPassword())
                }
            }
            is LoginActions.OnTapSignUpAction -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(NavigateToSignUp())
                }
            }
            is LoginActions.OnPasswordChanged -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            loading = false,
                            message ="",
                            password = action.password
                        )

                    }

                }
            }
            is LoginActions.OnErrorDialogDismissed -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            loading = false,
                            message ="",

                        )

                    }

                }
            }
        }
    }
}