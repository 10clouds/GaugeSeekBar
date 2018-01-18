package com.tenclouds.gaugeprogressbar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initProgress1View()
    }

    private fun initProgress1View() {
        val initialProgress = 0.1f
        progressText.text = String.format("%.2f", initialProgress)
        progress1.setProgress(initialProgress)

        progress1.progressChangedCallback = {
            progressText.text = String.format("%.2f", it)
        }
    }
}
