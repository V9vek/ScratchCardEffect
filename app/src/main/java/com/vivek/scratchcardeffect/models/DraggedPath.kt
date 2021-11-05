package com.vivek.scratchcardeffect.models

import androidx.compose.ui.graphics.Path

data class DraggedPath(
    val path: Path,
    val width: Float = 50f
)