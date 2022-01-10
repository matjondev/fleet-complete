package com.reactive.template.ui.screens.main

import android.view.LayoutInflater
import com.reactive.template.R
import com.reactive.template.base.BaseFragment
import com.reactive.template.databinding.ScreenMainBinding

class MainScreen : BaseFragment<ScreenMainBinding>() {

    override fun getBinding(inflater: LayoutInflater) = ScreenMainBinding.inflate(inflater)

    override fun initialize() {

    }
}