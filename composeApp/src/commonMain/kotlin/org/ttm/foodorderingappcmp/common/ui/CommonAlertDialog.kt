package org.ttm.foodorderingappcmp.common.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM

@Composable
fun CommonAlertDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: (() -> Unit)? = null, // optional cancel
    confirmText: String = "OK",
    dismissText: String = "Cancel",
    isDismissible: Boolean = false // control outside tap or back press
) {
    AlertDialog(
        onDismissRequest = {
            if (isDismissible) onDismiss?.invoke()
        },
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirmText)
            }
        },
        dismissButton = {
            onDismiss?.let {
                TextButton(onClick = it) {
                    Text(dismissText)
                }
            }
        },
        shape = RoundedCornerShape(MARGIN_MEDIUM)
    )
}
