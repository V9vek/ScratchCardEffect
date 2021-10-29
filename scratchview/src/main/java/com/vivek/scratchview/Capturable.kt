package com.vivek.scratchview

import android.graphics.Bitmap
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.drawToBitmap

/**
 * Adds ability to capture the [Composable] component in the form of [Bitmap]
 *
 * @param captureRequestKey Unique key for capture request
 * @param onBitmapCaptured Callback providing captured [Bitmap] of a [content]
 * @param content Composable content to be captured
 */
@Composable
fun Capturable(
    captureRequestKey: Any? = null,
    onBitmapCaptured: (Bitmap) -> Unit,
    content: @Composable () -> Unit
) {
    val latestCapturedCallback by rememberUpdatedState(onBitmapCaptured)

    val context = LocalContext.current
    val view = remember { ComposeView(context) }

    AndroidView(
        factory = {
            view.apply {
                setContent {
                    Surface(color = MaterialTheme.colors.background) {
                        content()
                    }
                }
            }
        }
    )

    // If key is changed it means it's requested to capture a Bitmap
    LaunchedEffect(captureRequestKey) {
        if (captureRequestKey != null) {
            view.post {
                latestCapturedCallback(view.drawToBitmap())
            }
        }
    }
}