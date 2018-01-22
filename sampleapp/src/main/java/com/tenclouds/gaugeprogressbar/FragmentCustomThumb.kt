package com.tenclouds.gaugeprogressbar

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_custom_thumb.*
import kotlinx.android.synthetic.main.fragment_custom_thumb.view.*

class FragmentCustomThumb : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_custom_thumb, container, false)
        view.progress.progressChangedCallback = {
            progressText.text = String.format("%.2f", it)
        }
        return view
    }
}