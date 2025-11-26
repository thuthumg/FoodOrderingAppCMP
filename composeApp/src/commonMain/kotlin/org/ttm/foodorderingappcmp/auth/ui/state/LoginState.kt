package org.ttm.foodorderingappcmp.auth.ui.state


data class LoginState (
    val loading: Boolean = false,
    val message: String = "",
    val email: String = "",
    val password: String = "")