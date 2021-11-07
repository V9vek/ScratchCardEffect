package com.vivek.scratchcardeffect.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

fun View.takeScreenShot() : Bitmap {
    val bitmap = Bitmap.createBitmap(
        this.width,
        this.height,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    this.draw(canvas)
    return bitmap
}