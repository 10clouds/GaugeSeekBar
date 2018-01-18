package com.tenclouds.gaugeprogress

import android.graphics.*
import android.graphics.drawable.Drawable

class ThumbDrawable(thumbColor: Int) : Drawable() {

    private val whitePaint = Paint().apply {
        color = Color.WHITE
        alpha = 255
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val thumbOuterPaint = Paint().apply {
        isAntiAlias = true
        color = thumbColor
        alpha = 102
    }

    private val thumbInnerPaint = Paint().apply {
        isAntiAlias = true
        color = thumbColor
    }

    override fun draw(canvas: Canvas) {
        val centerX = bounds.exactCenterX()
        val centerY = bounds.exactCenterY()
        val radius = centerX - bounds.left

        canvas.apply {
            drawCircle(centerX, centerY, radius, thumbOuterPaint)
            drawCircle(centerX, centerY, radius / 2f, thumbInnerPaint)
            drawCircle(centerX, centerY, 3f, whitePaint)
        }
    }

    override fun setAlpha(alpha: Int) {}

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun setColorFilter(colorFilter: ColorFilter?) {}
}