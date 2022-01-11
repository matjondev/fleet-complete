package com.reactive.template.di

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.reactive.template.base.BaseViewModel
import com.reactive.template.utils.preferences.SharedManager
import com.google.gson.Gson
import com.reactive.template.network.ApiInterface
import com.reactive.template.network.RetrofitClient
import com.reactive.template.utils.Constants
import com.reactive.template.utils.extensions.loge
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val viewModelModule = module {

    fun provideMutableLiveData() = MutableLiveData<Any>()

    viewModel { BaseViewModel(get(), get(), get()) }

    single { provideMutableLiveData() }

    single(named("sharedLive")) { provideMutableLiveData() }
}

val networkModule = module {

    fun provideGson() = Gson()

    single { provideGson() }

    factory(named("api")) {
        RetrofitClient
            .getRetrofit(Constants.BASE_URL, get(), get(), get())
            .create(ApiInterface::class.java)
    }

}

val sharedPrefModule = module {

    fun providePreferences(context: Context, name: String = context.packageName) =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    factory { providePreferences(get()) }

    factory { SharedManager(get(), get()) }
}

val modulesList = listOf(viewModelModule, networkModule, sharedPrefModule)


