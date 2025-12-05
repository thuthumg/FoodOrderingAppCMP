package org.ttm.foodorderingappcmp.features.profile.events

sealed class ProfileEvents {

    class OnNavigateToAbout: ProfileEvents()
    class OnNavigateToLogin: ProfileEvents()
}