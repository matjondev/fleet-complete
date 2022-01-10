package com.reactive.template.ui.screens.main

import android.view.LayoutInflater
import com.reactive.template.base.BaseFragment
import com.reactive.template.databinding.ScreenMapBinding

class MapScreen : BaseFragment<ScreenMapBinding>() {

    override fun getBinding(inflater: LayoutInflater) = ScreenMapBinding.inflate(inflater)

    override fun initialize() {
    }

    override fun observe() {
    }

}
