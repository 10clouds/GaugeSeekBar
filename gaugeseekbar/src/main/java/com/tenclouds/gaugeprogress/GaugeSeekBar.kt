package com.tenclouds.gaugeprogress

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.PointF
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.tenclouds.gaugeseekbar.R

class GaugeSeekBar : View {

    private companion object {
        private const val DEFAULT_START_ANGLE_DEG = 30f
        private const val DEFAULT_THUMB_RADIUS_DP = 11
        private const val DEFAULT_TRACK_WIDTH_DP = 8
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        applyAttributes(context.theme.obtainStyledAttributes(attrs, R.styleable.GaugeSeekBar, 0, 0))
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        applyAttributes(context.theme.obtainStyledAttributes(attrs, R.styleable.GaugeSeekBar, 0, 0))
    }

    private var thumbRadius = DEFAULT_THUMB_RADIUS_DP * resources.displayMetrics.density
    private var trackWidth = DEFAULT_TRACK_WIDTH_DP * resources.displayMetrics.density
    private var trackGradientArray: IntArray = context.resources.getIntArray(R.array.default_track_gradient)
    private var progressGradientArray = context.resources.getIntArray(R.array.default_index_gradient)
    private var progressGradientArrayPositions: FloatArray? = null
    private var thumbColor: Int = ContextCompat.getColor(context, R.color.default_thumb_color)
    private var startAngle = DEFAULT_START_ANGLE_DEG
    private var thumbDrawableId: Int = 0

    private var trackDrawable: TrackDrawable? = null
    private var progressDrawable: ProgressDrawable? = null
    private var thumbEntity: ThumbEntity? = null

    var showThumb: Boolean = true
        set(value) {
            field = value
            invalidate()
        }

    var interactive: Boolean = true

    var progress: Float = 0f
        set(value) {
            field = when {
                value in 0f..1f -> value
                value > 1f -> 1f
                else -> 0f
            }
            invalidate()
        }

    var progressChangedCallback: (progress: Float) -> Unit = {}

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    fun setTrackWidthDp(trackWidthDp: Int) {
        trackWidth = trackWidthDp * resources.displayMetrics.density
        invalidate()
    }

    private fun applyAttributes(attributes: TypedArray) {
        try {
            startAngle = attributes.getFloat(R.styleable.GaugeSeekBar_startAngleDegrees, startAngle)
            thumbRadius = attributes.getDimension(R.styleable.GaugeSeekBar_thumbRadius, thumbRadius)
            thumbColor = attributes.getColor(R.styleable.GaugeSeekBar_thumbColor, thumbColor)
            val trackGradientArrayId = attributes.getResourceId(R.styleable.GaugeSeekBar_trackGradient, 0)
            if (trackGradientArrayId != 0) {
                trackGradientArray = resources.getIntArray(trackGradientArrayId)
            }

            val trackGradientArrayPositionsResourceId = attributes.getResourceId(R.styleable.GaugeSeekBar_trackGradientPositions, 0)
            if (trackGradientArrayPositionsResourceId != 0) {
                val positionsIntArray = resources.getIntArray(trackGradientArrayPositionsResourceId)
                progressGradientArrayPositions = FloatArray(positionsIntArray.size) { positionsIntArray[it].div(100f) }
            }

            showThumb = attributes.getBoolean(R.styleable.GaugeSeekBar_showThumb, showThumb)
            trackWidth = attributes.getDimension(R.styleable.GaugeSeekBar_trackWidth, trackWidth)
            progress = attributes.getFloat(R.styleable.GaugeSeekBar_progress, 0f)

            val gradientArrayResourceId = attributes.getResourceId(R.styleable.GaugeSeekBar_progressGradient, 0)
            if (gradientArrayResourceId != 0) {
                progressGradientArray = resources.getIntArray(gradientArrayResourceId)
            }
            val gradientArrayPositionsResourceId = attributes.getResourceId(R.styleable.GaugeSeekBar_progressGradientPositions, 0)
            if (gradientArrayPositionsResourceId != 0) {
                val positionsIntArray = resources.getIntArray(gradientArrayPositionsResourceId)
                progressGradientArrayPositions = FloatArray(positionsIntArray.size) { positionsIntArray[it].div(100f) }
            }

            interactive = attributes.getBoolean(R.styleable.GaugeSeekBar_interactive, interactive)
            thumbDrawableId = attributes.getResourceId(R.styleable.GaugeSeekBar_thumbDrawable, 0)
        } finally {
            attributes.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        init(measuredWidth / 2f, measuredHeight / 2f)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean =
            if (interactive) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        performClick()
                        handleMotionEvent(event)
                    }
                    MotionEvent.ACTION_MOVE -> {
                        handleMotionEvent(event)
                    }
                }
                true
            } else {
                super.onTouchEvent(event)
            }

    private fun handleMotionEvent(event: MotionEvent) {
        val relativeX = measuredWidth / 2f - event.x
        val relativeY = event.y - measuredHeight / 2f
        val angle = Math.toDegrees(Math.atan2(relativeX.toDouble(), relativeY.toDouble()))
        progress = angleToProgress(if (angle > 0) angle else angle + 360f)
        progressChangedCallback.invoke(progress)
    }

    private fun angleToProgress(angle: Double): Float {
        val availableAngle = 360 - 2 * startAngle
        val relativeAngle = angle - startAngle
        return (relativeAngle / availableAngle).toFloat()
    }

    private fun init(centerX: Float, centerY: Float) {
        val centerPosition = PointF(centerX, centerY)
        val radiusPx = Math.min(centerX, centerY)
        val margin = Math.max(thumbRadius, trackWidth / 2f)
        trackDrawable = TrackDrawable(centerPosition, radiusPx, margin, trackGradientArray, startAngle, trackWidth)
        progressDrawable = ProgressDrawable(centerPosition, progress, radiusPx, margin, progressGradientArray, startAngle, trackWidth, progressGradientArrayPositions)
        thumbEntity = ThumbEntity(centerPosition, thumbColor, progress, startAngle, thumbRadius, if (thumbDrawableId != 0) ContextCompat.getDrawable(context, thumbDrawableId) else null)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            trackDrawable?.draw(this)
            progressDrawable?.draw(this, progress)

            if (showThumb) {
                thumbEntity?.draw(canvas, progress)
            }
        }
    }
}
