package com.tenclouds.gaugeprogress

import android.graphics.*

class Thumb(centerPosition: PointF,
            var progress: Float,
            private val gradientArray: IntArray,
            private val startAngle: Float,
            private val thumbRadius: Float,
            gradientArrayPositions: FloatArray? = null) : DrawableEntity(centerPosition) {

    private val gradientPositionsArray: FloatArray = gradientArrayPositions ?: FloatArray(gradientArray.size) { it.toFloat() / (gradientArray.size - 1) }

    companion object {
        private const val DEGREE_TO_RADIAN_RATIO = 0.0174533
    }

    private val whitePaint = Paint().apply {
        color = Color.WHITE
        alpha = 255
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val thumbOuterPaint = Paint().apply {
        isAntiAlias = true
    }

    private val thumbInnerPaint = Paint().apply {
        isAntiAlias = true
    }

    fun draw(canvas: Canvas, progress: Float) {
        this.progress = progress
        draw(canvas)
    }

    override fun draw(canvas: Canvas) {
        updateIndicatorPaint()
        val radius = Math.min(centerPosition.x, centerPosition.y) - thumbRadius

        val angle = (startAngle + (360 - 2 * startAngle) * progress) * DEGREE_TO_RADIAN_RATIO

        val indicatorX = centerPosition.x - Math.sin(angle) * radius
        val indicatorY = Math.cos(angle) * radius + centerPosition.y

        canvas.apply {
            drawCircle(indicatorX.toFloat(), indicatorY.toFloat(), thumbRadius, thumbOuterPaint)
            drawCircle(indicatorX.toFloat(), indicatorY.toFloat(), thumbRadius / 2f, thumbInnerPaint)
            drawCircle(indicatorX.toFloat(), indicatorY.toFloat(), 3f, whitePaint)
        }
    }

    private fun updateIndicatorPaint() {
        val paintColor = getColorForPercent(progress)
        val red = paintColor shr 16 and 0xff
        val green = paintColor shr 8 and 0xff
        val blue = paintColor and 0xff

        thumbInnerPaint.color = Color.argb(255, red, green, blue)
        thumbOuterPaint.color = Color.argb(102, red, green, blue)
    }

    private fun getColorForPercent(progress: Float): Int {
        return if (progress < 1f && gradientArray.size > 1) {
            val startIndex = gradientPositionsArray.indexOfLast { progress >= it }
            val endIndex = if (startIndex + 1 < gradientPositionsArray.size) startIndex + 1 else gradientPositionsArray.size - 1
            val startPosition = gradientPositionsArray[startIndex]
            val endPosition = gradientPositionsArray[endIndex]
            val relativePosition = (progress - startPosition) / (endPosition - startPosition)

            val startColor = gradientArray[startIndex]
            val endColor = gradientArray[endIndex]

            val inverseWeight = 1 - relativePosition
            Color.rgb(Math.round(Color.red(startColor) * inverseWeight + Color.red(endColor) * relativePosition),
                    Math.round(Color.green(startColor) * inverseWeight + Color.green(endColor) * relativePosition),
                    Math.round(Color.blue(startColor) * inverseWeight + Color.blue(endColor) * relativePosition))
        } else {
            gradientArray.last()
        }
    }

    override fun setAlpha(alpha: Int) {}

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun setColorFilter(colorFilter: ColorFilter?) {}


}