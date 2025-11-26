package org.ttm.foodorderingappcmp.auth.events


sealed class RegisterEvents {
    class NavigateToHome : RegisterEvents()
}