package org.ttm.foodorderingappcmp.app.state

import org.ttm.foodorderingappcmp.auth.data.vos.LoginRegisterVO

data class AppState(
    val loginStatus: Boolean = false,
    val userData: LoginRegisterVO? = null
)
