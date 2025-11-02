package org.ttm.foodorderingappcmp.auth.ui.state

import org.ttm.foodorderingappcmp.auth.data.vos.LoginRegisterVO

data class LoginRegisterState (
    val loginRegisterVO: LoginRegisterVO? = null,
    val loading: Boolean = false,
    val message: String = "",
    val successStatus: Boolean = false,
    val dismissStatus: Boolean = true,
)