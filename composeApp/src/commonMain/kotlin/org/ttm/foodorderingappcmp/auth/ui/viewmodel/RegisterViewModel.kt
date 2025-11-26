package org.ttm.foodorderingappcmp.auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.auth.actions.RegisterActions
import org.ttm.foodorderingappcmp.auth.data.repository.LoginRegisterRepository
import org.ttm.foodorderingappcmp.auth.events.RegisterEvents
import org.ttm.foodorderingappcmp.auth.ui.state.RegisterState
import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.core.utils.apiToken
import org.ttm.foodorderingappcmp.core.utils.emailRegex

class RegisterViewModel : ViewModel() {

    val loginRegisterRepo = LoginRegisterRepository
    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()
    val passwordLength = 6
    private val _navigationSharedFlow: MutableSharedFlow<RegisterEvents> = MutableSharedFlow()
    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()


    fun onClickRegister() {

        val errorMessage = when {
            _registerState.value.fullName.isBlank() -> "Full name is required."
            _registerState.value.email.isBlank() -> "Email is required."
            !_registerState.value.email.matches(emailRegex) -> "Invalid email format."
            _registerState.value.password.isBlank() -> "Password is required."
            _registerState.value.password.length < passwordLength -> "Password must be at least 6 characters."
            else -> null
        }

        if (errorMessage != null) {
            _registerState.update {
                it.copy(
                    loading = false,
                    message = errorMessage
                )
            }
            return
        }

        viewModelScope.launch {
            _registerState.update {
                it.copy(
                    loading = true,
                    message = ""
                )
            }

            when (val result = loginRegisterRepo.register(
                email = _registerState.value.email,
                fullName = _registerState.value.fullName,
                password = _registerState.value.password
            )) {
                is Resource.Success ->{
                    _registerState.update {
                        it.copy(
                            loading = false,
                            message = ""
                        )
                    }

                    apiToken = result.data.accessToken ?: ""

                    _navigationSharedFlow.emit(RegisterEvents.NavigateToHome())
                }
                is Resource.Error -> _registerState.update {
                    it.copy(
                        loading = false,
                        message = result.message
                    )
                }
            }
        }
    }

    fun onAction(action: RegisterActions) {
        when (action) {
            is RegisterActions.OnTapCreateAccountAction -> {
                onClickRegister()
            }

            is RegisterActions.OnEmailChanged -> {
                viewModelScope.launch {
                    _registerState.update {
                        it.copy(
                            loading = false,
                            message = "",
                            email = action.email
                        )

                    }

                }
            }

            is RegisterActions.OnPasswordChanged -> {
                viewModelScope.launch {
                    _registerState.update {
                        it.copy(
                            loading = false,
                            message = "",
                            password = action.password
                        )

                    }

                }
            }

            is RegisterActions.OnFullNameChanged -> {
                viewModelScope.launch {
                    _registerState.update {
                        it.copy(
                            loading = false,
                            message = "",
                            fullName = action.fullName
                        )

                    }

                }
            }

            is RegisterActions.OnErrorDialogDismissed -> {
                viewModelScope.launch {
                    _registerState.update {
                        it.copy(
                            loading = false,
                            message = ""
                        )

                    }

                }
            }
        }
    }
}