package com.tenclouds.gaugeprogress

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View

class GaugeProgressBar : View {

    private companion object {
        private const val START_ANGLE_DEG = 30f
        private const val DEFAULT_THUMB_RADIUS_DP = 11
        private const val DEFAULT_TRACK_WIDTH_DP = 8
        private const val DEFAULT_TRACK_COLOR = -3090191
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        applyAttributes(context.theme.obtainStyledAttributes(attrs, R.styleable.GaugeProgressBar, 0, 0))
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        applyAttributes(context.theme.obtainStyledAttributes(attrs, R.styleable.GaugeProgressBar, 0, 0))
    }

    private var thumbRadius = DEFAULT_THUMB_RADIUS_DP * resources.displayMetrics.density
    private var trackWidth = DEFAULT_TRACK_WIDTH_DP * resources.displayMetrics.density
    private var trackColor: Int = DEFAULT_TRACK_COLOR
    private var gradientArray = context.resources.getIntArray(R.array.index_gradient)
    private var gradientArrayPositions: FloatArray? = null

    private var track: Track? = null
    private var progressDrawable: ProgressDrawable? = null
    private var thumb: Thumb? = null

    var showThumb: Boolean = true

    var progress: Float = 0f
        set(value) {
            field = when {
                value in 0f..1f -> value
                value > 1f -> 1f
                else -> 0f
            }
            invalidate()
        }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    fun setTrackWidthDp(trackWidthDp: Int) {
        trackWidth = trackWidthDp * resources.displayMetrics.density
        invalidate()
    }

    private fun applyAttributes(attributes: TypedArray) {
        try {
            thumbRadius = attributes.getDimension(R.styleable.GaugeProgressBar_thumbRadius, thumbRadius)
            trackColor = attributes.getColor(R.styleable.GaugeProgressBar_trackColor, trackColor)
            showThumb = attributes.getBoolean(R.styleable.GaugeProgressBar_showThumb, showThumb)
            trackWidth = attributes.getDimension(R.styleable.GaugeProgressBar_trackWidth, trackWidth)
            progress = attributes.getFloat(R.styleable.GaugeProgressBar_progress, 0f)

            val gradientArrayResourceId = attributes.getResourceId(R.styleable.GaugeProgressBar_progressGradient, 0)
            if (gradientArrayResourceId != 0) {
                gradientArray = resources.getIntArray(gradientArrayResourceId)
            }
            val gradientArrayPositionsResourceId = attributes.getResourceId(R.styleable.GaugeProgressBar_progressGradientPositions, 0)
            if (gradientArrayPositionsResourceId != 0) {
                val positionsIntArray = resources.getIntArray(gradientArrayPositionsResourceId)
                gradientArrayPositions = FloatArray(positionsIntArray.size) { positionsIntArray[it].div(100f) }
            }

        } finally {
            attributes.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        init(measuredWidth / 2f, measuredHeight / 2f)
    }

    private fun init(centerX: Float, centerY: Float) {
        val centerPosition = PointF(centerX, centerY)
        val radiusPx = Math.min(centerX, centerY)
        val margin = Math.max(thumbRadius, trackWidth / 2f)
        track = Track(centerPosition, radiusPx, margin, trackColor, START_ANGLE_DEG, trackWidth)
        progressDrawable = ProgressDrawable(centerPosition, 0f, radiusPx, margin, gradientArray, START_ANGLE_DEG, trackWidth, gradientArrayPositions)
        thumb = Thumb(centerPosition, 0f, context.resources.getIntArray(R.array.index_gradient), START_ANGLE_DEG, thumbRadius)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            track?.draw(this)
            progressDrawable?.draw(this, progress)

            if (showThumb) {
                thumb?.draw(canvas, progress)
            }
        }
    }
}
