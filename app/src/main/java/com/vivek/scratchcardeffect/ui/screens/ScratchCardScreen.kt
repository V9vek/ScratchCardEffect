package com.vivek.scratchcardeffect.ui.screens

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.vivek.scratchcardeffect.R
import com.vivek.scratchcardeffect.models.DraggedPath
import com.vivek.scratchcardeffect.utils.takeScreenShot
import com.vivek.scratchview.ScratchingCanvas

@ExperimentalComposeUiApi
@Composable
fun ScratchCardScreen() {
    val overlayImage = ImageBitmap.imageResource(id = R.drawable.overlay_image)
    val baseImage = ImageBitmap.imageResource(id = R.drawable.base_image)

    val currentPathState = remember { mutableStateOf(DraggedPath(path = Path())) }
    val movedOffsetState = remember { mutableStateOf<Offset?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        IconButton(
            onClick = {
                movedOffsetState.value = null
                currentPathState.value = DraggedPath(path = Path())
            },
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Icon(
                imageVector = Icons.Default.Clear, contentDescription = "Clear",
                tint = MaterialTheme.colors.onPrimary
            )
        }

        // Scratch Card Implementation
        ScratchingCanvas(
            overlayImage,
            modifier = Modifier
                .align(Alignment.Center)
                .size(220.dp)
                .clip(RoundedCornerShape(size = 16.dp)),
            movedOffset = movedOffsetState.value,
            onMovedOffset = { x, y ->
                movedOffsetState.value = Offset(x, y)
            },
            currentPath = currentPathState.value.path,
            currentPathThickness = currentPathState.value.width,
            background = {
                Image(bitmap = baseImage, contentDescription = "", modifier = Modifier.size(220.dp))
            }
        )
    }
}





