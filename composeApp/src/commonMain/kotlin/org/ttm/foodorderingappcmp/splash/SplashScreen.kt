package org.ttm.foodorderingappcmp.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.order_confirm_pic
import org.jetbrains.compose.resources.painterResource
import org.ttm.foodorderingappcmp.core.LOGO_HEIGHT
import org.ttm.foodorderingappcmp.core.LOGO_WIDTH
import org.ttm.foodorderingappcmp.core.MARGIN_XLARGE

@Composable
fun SplashScreen() {
    Box(modifier = Modifier.fillMaxSize().background(Color.White)){
        // Logo
        Image(
            painterResource(Res.drawable.order_confirm_pic),
            contentDescription = null,
            modifier = Modifier.width(LOGO_WIDTH).height(LOGO_HEIGHT)
                .padding(top = MARGIN_XLARGE)
                .align(Alignment.Center)
        )
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}