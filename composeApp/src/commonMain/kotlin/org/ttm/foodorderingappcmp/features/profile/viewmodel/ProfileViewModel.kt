package org.ttm.foodorderingappcmp.features.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ttm.foodorderingappcmp.app.data.AppRepository
import org.ttm.foodorderingappcmp.features.profile.actions.ProfileActions
import org.ttm.foodorderingappcmp.features.profile.events.ProfileEvents
import org.ttm.foodorderingappcmp.features.profile.events.ProfileEvents.*
import org.ttm.foodorderingappcmp.features.profile.state.ProfileState

class ProfileViewModel: ViewModel() {

    val appRepository = AppRepository
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _navigationSharedFlow = MutableSharedFlow<ProfileEvents>()
    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()

    init {
        getUserNameAndEmail()
    }

    fun getUserNameAndEmail() = viewModelScope.launch {
        try {

            val user = appRepository.getAllUserData().firstOrNull()

            if (user != null && user.accessToken?.isNotBlank() == true) {

                _state.update {
                    it.copy(
                       // profileStatus = true,
                        email = user.email,
                        userName = user.fullName,
                       // logoutStatus = false
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        message = "",
                        alertDialogTitle = ""
                    )
                }
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    message = e.message.toString(),
                    alertDialogTitle = "Error"
                )
            }
        }
    }

    fun onAction(profileAction: ProfileActions){
        when(profileAction){

            is ProfileActions.OnTapAbout -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(OnNavigateToAbout())
                }

            }
            is ProfileActions.OnTapLogout -> {
                _state.update {
                    it.copy(
                        message = "Are you sure you want to log out? All locally stored app data will be lost.",
                        alertDialogTitle = "Logout"
                    )
                }
            }

            is ProfileActions.OnLogoutDialogDismissed -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            message = "",
                            alertDialogTitle = ""
                        )
                    }

                    launch {
                        _navigationSharedFlow.emit(OnNavigateToLogin())
                    }
                }

            }
        }
    }

}