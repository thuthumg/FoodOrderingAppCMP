package org.ttm.foodorderingappcmp

import androidx.compose.ui.window.ComposeUIViewController
import org.ttm.foodorderingappcmp.core.persistence.getDatabaseBuilderIOS

fun MainViewController() = ComposeUIViewController {
    val databaseBuilder = getDatabaseBuilderIOS()
    App(databaseBuilder)
}