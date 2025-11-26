package org.ttm.foodorderingappcmp.auth.ui.state


data class RegisterState(
    val loading: Boolean = false,
    val message: String = "",
    val email: String = "",
    val password: String = "",
    val fullName: String = "")