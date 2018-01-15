package com.tenclouds.gaugeprogress

import android.graphics.*

class Track(centerPosition: PointF,
            private val radiusPx: Float,
            private val margin: Float,
            private val color: Int,
            private val startAngle: Float,
            private val trackWidthPx: Float) : DrawableEntity(centerPosition) {

    private val paint = Paint().apply {
        color = this@Track.color
        strokeWidth = trackWidthPx
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }

    override fun draw(canvas: Canvas) {
        val rect = RectF(centerPosition.x - radiusPx + margin,
                centerPosition.y - radiusPx + margin,
                centerPosition.x + radiusPx - margin,
                centerPosition.y + radiusPx - margin)
        canvas.drawArc(rect,
                90f + startAngle,
                360f - 2 * startAngle,
                false,
                paint)
    }

    override fun setAlpha(alpha: Int) {}

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun setColorFilter(colorFilter: ColorFilter?) {}
}