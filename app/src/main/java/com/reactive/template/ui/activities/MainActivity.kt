package com.reactive.template.ui.activities

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import com.reactive.template.base.BaseViewModel
import com.reactive.template.R
import com.reactive.template.base.BaseActivity
import com.reactive.template.base.initialFragment
import com.reactive.template.databinding.ActivityMainBinding
import com.reactive.template.ui.screens.splash.SplashScreen
import com.reactive.template.utils.extensions.showGone
import com.reactive.template.utils.preferences.SharedManager
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding() =
        ActivityMainBinding.inflate(LayoutInflater.from(applicationContext))

    val viewModel by viewModel<BaseViewModel>()
    val sharedManager: SharedManager by inject()

    override fun onActivityCreated() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        init()
    }

    private fun init() {
        viewModel.apply {
            parentLayoutId = R.id.fragmentContainer

            fetchData()
        }

        startFragment()
    }

    private fun startFragment() {
        initialFragment(SplashScreen())
    }

    fun showProgress(show: Boolean) {
        binding.progressBar.showGone(show)
    }
}
