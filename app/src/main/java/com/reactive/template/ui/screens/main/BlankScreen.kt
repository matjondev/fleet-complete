package com.reactive.template.ui.screens.main

import android.view.LayoutInflater
import com.reactive.template.R
import com.reactive.template.base.BaseFragment
import com.reactive.template.databinding.ScreenBlankBinding

class BlankScreen : BaseFragment<ScreenBlankBinding>() {

    override fun getBinding(inflater: LayoutInflater) = ScreenBlankBinding.inflate(inflater)

    override fun initialize() {

    }
}