package com.tenclouds.gaugeseekbar

import android.graphics.*

class ProgressDrawable(position: PointF,
                       private var progress: Float,
                       private val radiusPx: Float,
                       private val margin: Float,
                       private val gradientArray: IntArray,
                       private val startAngle: Float,
                       private val trackWidthPx: Float,
                       gradientPositionsArray: FloatArray? = null) : DrawableEntity(position) {

    private val gradientPositionsArray: FloatArray =
            if (gradientPositionsArray != null) {
                getGradientPositions(gradientPositionsArray)
            } else getGradientPositions(
                    FloatArray(gradientArray.size) {
                        it.toFloat() / (gradientArray.size - 1)
                    })

    init {
        if (gradientArray.size != this.gradientPositionsArray.size)
            throw IllegalStateException("gradientArray and gradientPositionsArray sizes must be equal.")
    }

    private val progressPaint = Paint().apply {
        strokeWidth = trackWidthPx
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE

        if (gradientArray.size > 1) {
            val shader = createSweepGradient()
            setShader(shader)
        } else {
            color = gradientArray[0]
        }
    }

    private fun createSweepGradient(): SweepGradient {
        val shader = SweepGradient(centerPosition.x, centerPosition.y, gradientArray, gradientPositionsArray)
        val gradientRotationMatrix = Matrix()
        //code need to account for path width
        val angularMargin = Math.toDegrees(2 * Math.asin((trackWidthPx / radiusPx).toDouble())).toFloat()
        gradientRotationMatrix.preRotate(90f + startAngle - angularMargin, centerPosition.x, centerPosition.y)
        shader.setLocalMatrix(gradientRotationMatrix)
        return shader
    }

    private fun getGradientPositions(gradientPositions: FloatArray): FloatArray {
        val normalizedStartAngle = startAngle / 360f
        val normalizedAvailableSpace = 1f - 2 * normalizedStartAngle

        return FloatArray(gradientPositions.size) {
            normalizedStartAngle + normalizedAvailableSpace * gradientPositions[it]
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

    override fun setAlpha(alpha: Int) {}

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun setColorFilter(colorFilter: ColorFilter?) {}
}