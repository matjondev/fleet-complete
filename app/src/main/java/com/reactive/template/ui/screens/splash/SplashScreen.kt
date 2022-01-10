package com.reactive.template.ui.screens.splash

import android.os.CountDownTimer
import android.view.LayoutInflater
import com.reactive.template.base.BaseFragment
import com.reactive.template.databinding.ScreenSplashBinding
import com.reactive.template.ui.screens.main.MapScreen

class SplashScreen : BaseFragment<ScreenSplashBinding>() {

    override fun getBinding(inflater: LayoutInflater)=ScreenSplashBinding.inflate(inflater)

    override fun initialize() {
        object : CountDownTimer(1500, 1000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                replaceFragment(MapScreen())
            }
        }.start()
    }
}