package org.ttm.foodorderingappcmp.auth.actions

sealed class RegisterActions {

    data class OnEmailChanged(val email: String) : RegisterActions()
    data class OnPasswordChanged(val password: String): RegisterActions()
    data class OnFullNameChanged(val fullName: String): RegisterActions()

    class OnTapCreateAccountAction() : RegisterActions()

    class OnErrorDialogDismissed() : RegisterActions()
}