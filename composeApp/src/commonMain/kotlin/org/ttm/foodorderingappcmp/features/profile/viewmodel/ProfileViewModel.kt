package org.ttm.foodorderingappcmp.features.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.app.data.AppRepository
import org.ttm.foodorderingappcmp.features.profile.state.ProfileState

class ProfileViewModel: ViewModel() {

    val appRepository = AppRepository
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        getUserNameAndEmail()
    }

    fun getUserNameAndEmail() = viewModelScope.launch {
        try {

            val user = appRepository.getAllUserData().firstOrNull()

            if (user != null && user.accessToken?.isNotBlank() == true) {

                _state.update {
                    it.copy(
                        profileStatus = true,
                        email = user.email,
                        userName = user.fullName,
                        logoutStatus = false
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        profileStatus = false,
                        email = "",
                        userName = "",
                        logoutStatus = false
                    )
                }
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    profileStatus = false,
                    email = "",
                    userName = "",
                    logoutStatus = false
                )
            }
        }
    }

    fun onDismissErrorAlertDialog() {
        _state.update {
            it.copy(userName = "", email = "", profileStatus = false,logoutStatus = true)
        }
    }

}