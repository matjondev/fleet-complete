package com.reactive.template.network

import android.content.Context
import com.google.gson.Gson
import com.reactive.template.BuildConfig
import com.reactive.template.utils.network.UnsafeOkHttpClient
import com.reactive.template.utils.preferences.SharedManager
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

object RetrofitClient {

    fun getRetrofit(
        baseUrl: String,
        sharedManager: SharedManager,
        context: Context,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getClient(sharedManager, context))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun getClient(sharedManager: SharedManager, context: Context): OkHttpClient {
        val builder = UnsafeOkHttpClient.getUnsafeOkHttpClientBuilder()
        val interceptor = HttpLoggingInterceptor { message -> Timber.tag("TTT").i(message) }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        builder.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
            if (sharedManager.token.isNotEmpty()) request.addHeader("Authorization", "Bearer ${sharedManager.token}")
            chain.proceed(request.build())
        })
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(interceptor)
            builder.addInterceptor(ChuckInterceptor(context))
        }
        return builder.build()
    }
}
