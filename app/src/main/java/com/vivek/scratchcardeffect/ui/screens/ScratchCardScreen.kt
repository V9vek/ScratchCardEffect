package com.vivek.scratchcardeffect.ui.screens

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.vivek.scratchcardeffect.R
import com.vivek.scratchcardeffect.models.DraggedPath

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
            overlayImage = overlayImage,
            baseImage = baseImage,
            modifier = Modifier.align(Alignment.Center),
            movedOffset = movedOffsetState.value,
            onMovedOffset = { x, y ->
                movedOffsetState.value = Offset(x, y)
            },
            currentPath = currentPathState.value.path,
            currentPathThickness = currentPathState.value.width,
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun ScratchingCanvas(
    overlayImage: ImageBitmap,
    baseImage: ImageBitmap,
    modifier: Modifier = Modifier,
    movedOffset: Offset?,
    onMovedOffset: (Float, Float) -> Unit,
    currentPath: Path,
    currentPathThickness: Float,
) {
    Canvas(
        modifier = modifier
            .size(220.dp)
            .clipToBounds()
            .clip(RoundedCornerShape(size = 16.dp))
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        println("CurrentPath/ACTION_DOWN: (${it.x}, ${it.y})")
                        currentPath.moveTo(it.x, it.y)
                    }
                    MotionEvent.ACTION_MOVE -> {
                        println("MovedOffset/ACTION_MOVE: (${it.x}, ${it.y})")
                        onMovedOffset(it.x, it.y)
                    }
                }
                true
            }
    ) {
        val canvasWidth = size.width.toInt()
        val canvasHeight = size.height.toInt()
        val imageSize = IntSize(width = canvasWidth, height = canvasHeight)

        // Overlay Image to be scratched
        drawImage(
            image = overlayImage,
            dstSize = imageSize
        )

        movedOffset?.let {
            currentPath.addOval(oval = Rect(it, currentPathThickness))
        }

        clipPath(path = currentPath, clipOp = ClipOp.Intersect) {
            // Base Image after scratching
            drawImage(
                image = baseImage,
                dstSize = imageSize
            )
        }
    }
}





