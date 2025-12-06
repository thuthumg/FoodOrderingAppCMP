package org.ttm.foodorderingappcmp.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import org.ttm.foodorderingappcmp.core.MARGIN_LARGE
import org.ttm.foodorderingappcmp.core.MARGIN_XLARGE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingDialog(
    onDismissRequest : () -> Unit,
){

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        modifier = Modifier.background(
            color = Color.Transparent,
            // shape = RoundedCornerShape(MARGIN_MEDIUM)
        )
            .padding(horizontal = MARGIN_LARGE, vertical = MARGIN_XLARGE)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(65.dp).background(color = Color.Transparent)){

            CircularProgressIndicator()
        }

    }
}