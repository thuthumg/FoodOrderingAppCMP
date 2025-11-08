package org.ttm.foodorderingappcmp.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.app.data.AppRepository
import org.ttm.foodorderingappcmp.app.state.AppState
import org.ttm.foodorderingappcmp.auth.data.repository.LoginRegisterRepository
import org.ttm.foodorderingappcmp.auth.ui.state.LoginRegisterState
import org.ttm.foodorderingappcmp.core.utils.apiToken

class AppViewModel: ViewModel() {
    val appRepository = AppRepository
    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    fun autoLogin() = viewModelScope.launch {
        try {

            val user = appRepository.getAllUserData().firstOrNull()

            if (user != null && user.accessToken.isNotBlank()) {
                //success
                apiToken = user.accessToken
                _state.update {
                    it.copy(
                       loginStatus = true,
                        userData = user
                    )
                }
            } else {
                _state.update {
                    it.copy(
                       loginStatus = false,
                        userData = null
                    )
                }
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    loginStatus = false,
                    userData = null
                )
            }
        }
    }
}