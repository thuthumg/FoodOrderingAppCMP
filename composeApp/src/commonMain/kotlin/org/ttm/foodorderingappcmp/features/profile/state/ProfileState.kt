package org.ttm.foodorderingappcmp.features.profile.state

data class ProfileState(
    val userName: String = "",
    val email: String = "",
    val profileStatus : Boolean = false,
    val logoutStatus: Boolean = false
)
