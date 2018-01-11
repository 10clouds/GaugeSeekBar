package com.tenclouds.gaugeprogress

import android.graphics.Canvas
import android.graphics.PointF

abstract class DrawableEntity(protected var centerPosition: PointF) {

    abstract fun draw(canvas: Canvas)
}