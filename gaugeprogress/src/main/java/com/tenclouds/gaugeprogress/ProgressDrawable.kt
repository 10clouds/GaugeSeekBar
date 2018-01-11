package com.tenclouds.gaugeprogress

import android.graphics.*

class ProgressDrawable(position: PointF,
                       var progress: Float,
                       private val radiusPx: Float,
                       private val margin: Float,
                       private val gradientArray: IntArray,
                       private val startAngle: Float,
                       private val trackWidthPx: Float,
                       gradientPositionsArray: FloatArray? = null) : DrawableEntity(position) {

    private val gradientPositionsArray: FloatArray = gradientPositionsArray ?: FloatArray(gradientArray.size) { it.toFloat() / gradientArray.size }

    init {
        if (gradientArray.size != this.gradientPositionsArray.size)
            throw IllegalStateException("gradientArray and gradientPositionsArray sizes must be equal.")
    }

    private val progressPaint = Paint().apply {
        val shader = createSweepGradient()
        strokeWidth = trackWidthPx
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
        setShader(shader)
    }

    private fun createSweepGradient(): SweepGradient {
        val shader = SweepGradient(centerPosition.x, centerPosition.y, gradientArray, getGradientPositions())
        val gradientRotationMatrix = Matrix()
        gradientRotationMatrix.preRotate(90f + startAngle - 5, centerPosition.x, centerPosition.y)
        shader.setLocalMatrix(gradientRotationMatrix)
        return shader
    }

    private fun getGradientPositions(): FloatArray {
        val normalizedStartAngle = startAngle / 360f
        val normalizedAvailableSpace = 1f - 2 * normalizedStartAngle

        return FloatArray(gradientArray.size) {
            normalizedStartAngle + normalizedAvailableSpace * gradientPositionsArray[it]
        }
    }

    fun draw(canvas: Canvas, progress: Float) {
        this.progress = progress
        draw(canvas)
    }

    override fun draw(canvas: Canvas) {
        val angle = ((360 - (startAngle * 2)) * progress)
        if (angle > 0) {
            val rect = RectF(centerPosition.x - radiusPx + margin,
                    centerPosition.y - radiusPx + margin,
                    centerPosition.x + radiusPx - margin,
                    centerPosition.y + radiusPx - margin)
            canvas.drawArc(rect,
                    90f + startAngle,
                    angle,
                    false,
                    progressPaint)
        }
    }
}