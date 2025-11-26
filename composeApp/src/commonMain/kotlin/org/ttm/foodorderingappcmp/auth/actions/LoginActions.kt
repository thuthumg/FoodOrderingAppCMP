package org.ttm.foodorderingappcmp.auth.actions

sealed class LoginActions {

    data class OnEmailChanged(val email: String) : LoginActions()
    data class OnPasswordChanged(val password: String): LoginActions()
     class OnTapLoginAction() : LoginActions()
    class OnTapForgotPasswordAction: LoginActions()
    class OnTapSignUpAction: LoginActions()

    class OnErrorDialogDismissed() : LoginActions()

}