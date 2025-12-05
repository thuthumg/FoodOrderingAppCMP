package org.ttm.foodorderingappcmp.features.profile.actions


sealed class ProfileActions {

    class OnTapAbout: ProfileActions()
    class OnTapLogout: ProfileActions()
    class OnLogoutDialogDismissed: ProfileActions()
}