package com.reactive.template.ui.screens

import android.view.LayoutInflater
import com.reactive.template.R
import com.reactive.template.base.BaseFragment
import com.reactive.template.databinding.ScreenBottomNavBinding

class BottomNavScreen : BaseFragment<ScreenBottomNavBinding>() {

    override fun getBinding(inflater: LayoutInflater) = ScreenBottomNavBinding.inflate(inflater)

    override fun initialize() {
    }

    override fun observe() {
    }

}
