package com.tenclouds.gaugeseekbar

import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ThumbEntityTest {
    @Test
    fun testThumbPositionAtHalfProgress() {
        val thumbDrawable = mock<Drawable>()

        ThumbEntity(PointF(20f, 20f), 0.5f, 27f, 5f, thumbDrawable)

        verify(thumbDrawable).setBounds(15, 0, 25, 10)
    }

    @Test
    fun testThumbPositionInAtZeroProgress() {
        val thumbDrawable = mock<Drawable>()

        ThumbEntity(PointF(20f, 20f), 0.0f, 0f, 5f, thumbDrawable)

        verify(thumbDrawable).setBounds(15, 30, 25, 40)
    }

    @Test
    fun testThumbPositionInAtOneProgress() {
        val thumbDrawable = mock<Drawable>()

        ThumbEntity(PointF(20f, 20f), 1.0f, 0f, 5f, thumbDrawable)

        verify(thumbDrawable).setBounds(14, 29, 24, 39)
    }

    @Test
    fun testThumbPositionInBottomLeftQuarter() {
        val thumbDrawable = mock<Drawable>()

        ThumbEntity(PointF(20f, 20f), 0.0f, 27f, 5f, thumbDrawable)

        verify(thumbDrawable).setBounds(8, 28, 18, 38)
    }
}