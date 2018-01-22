package com.tenclouds.gaugeseekbar

import android.graphics.*
import android.graphics.drawable.Drawable

class ThumbEntity(private val centerPosition: PointF,
                  thumbColor: Int,
                  private var progress: Float,
                  private val startAngle: Float,
                  private val thumbRadius: Float,
                  drawable: Drawable? = null) {

    companion object {
        private const val DEGREE_TO_RADIAN_RATIO = 0.0174533
    }

    private val thumbDrawable = drawable ?: ThumbDrawable(thumbColor)

    fun draw(canvas: Canvas, progress: Float) {
        this.progress = progress

        val seekbarRadius = Math.min(centerPosition.x, centerPosition.y) - thumbRadius

        val angle = (startAngle + (360 - 2 * startAngle) * progress) * DEGREE_TO_RADIAN_RATIO

        val indicatorX = centerPosition.x - Math.sin(angle) * seekbarRadius
        val indicatorY = Math.cos(angle) * seekbarRadius + centerPosition.y

        thumbDrawable.setBounds(
                (indicatorX - thumbRadius).toInt(),
                (indicatorY - thumbRadius).toInt(),
                (indicatorX + thumbRadius).toInt(),
                (indicatorY + thumbRadius).toInt())

        thumbDrawable.draw(canvas)
    }
}