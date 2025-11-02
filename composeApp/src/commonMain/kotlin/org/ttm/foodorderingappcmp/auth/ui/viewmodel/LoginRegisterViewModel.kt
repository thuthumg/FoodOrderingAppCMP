package org.ttm.foodorderingappcmp.auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.auth.data.LoginRegisterRepository
import org.ttm.foodorderingappcmp.auth.data.vos.LoginRegisterVO
import org.ttm.foodorderingappcmp.auth.ui.state.LoginRegisterState
import org.ttm.foodorderingappcmp.core.network.Resource
import org.ttm.foodorderingappcmp.core.utils.apiToken

class LoginRegisterViewModel : ViewModel() {

    val loginRegisterRepo = LoginRegisterRepository
    private val _state = MutableStateFlow(LoginRegisterState())
    val state = _state.asStateFlow()
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    val passwordLength = 6


    fun onClickLogin(email: String, password: String) {

        val errorMessage = when {
            email.isBlank() -> "Email is required."
            !email.matches(emailRegex) -> "Invalid email format."
            password.isBlank() -> "Password is required."
            password.length < passwordLength -> "Password must be at least 6 characters."
            else -> null
        }

        if (errorMessage != null) {
            _state.update {
                it.copy(
                    loading = false,
                    message = errorMessage,
                    successStatus = false,
                    dismissStatus = false,
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(loading = true, dismissStatus = true) }

            when (val result = loginRegisterRepo.login(email, password)) {
                is Resource.Success -> _state.update {
                    it.copy(
                        loginRegisterVO = result.data,
                        loading = false,
                        message = "",
                        successStatus = true,
                        dismissStatus = true,
                    )
                }

                is Resource.Error -> _state.update {
                    it.copy(
                        loading = false,
                        message = result.message,
                        successStatus = false,
                        dismissStatus = false,
                    )
                }

                Resource.Loading -> _state.update { it.copy(loading = true, dismissStatus = true) }
            }
        }

    }

    fun onClickRegister(email: String, fullName: String, password: String) {

        val errorMessage = when {
            fullName.isBlank() -> "Full name is required."
            email.isBlank() -> "Email is required."
            !email.matches(emailRegex) -> "Invalid email format."
            password.isBlank() -> "Password is required."
            password.length < passwordLength -> "Password must be at least 6 characters."
            else -> null
        }

        if (errorMessage != null) {
            _state.update {
                it.copy(
                    loading = false,
                    message = errorMessage,
                    successStatus = false,
                    dismissStatus = false
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(loading = true, dismissStatus = true) }

            when (val result = loginRegisterRepo.register(
                email = email,
                fullName = fullName,
                password = password
            )) {
                is Resource.Success -> _state.update {
                    it.copy(
                        loginRegisterVO = result.data,
                        loading = false,
                        message = "",
                        successStatus = true,
                        dismissStatus = true
                    )
                }

                is Resource.Error -> _state.update {
                    it.copy(
                        loading = false,
                        message = result.message,
                        successStatus = false,
                        dismissStatus = false
                    )
                }

                Resource.Loading -> _state.update { it.copy(loading = true, dismissStatus = true) }
            }
        }
    }

    fun onDismissErrorAlertDialog() {
        _state.update {
            it.copy(dismissStatus = true)
        }
    }

    fun clearUserData(){
        viewModelScope.launch {
            loginRegisterRepo.deleteAllUserData()
            _state.update {
                it.copy(
                    loading = false,
                    message ="",
                    successStatus = false,
                    dismissStatus = true
                )
            }
        }

    }
}