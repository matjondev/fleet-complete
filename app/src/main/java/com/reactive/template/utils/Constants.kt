package com.reactive.template.utils

import com.reactive.template.BuildConfig

object Constants {

    const val BASE_URL = BuildConfig.API_URL
    const val API_KEY = BuildConfig.API_KEY
    const val TIMEOUT = 10.toLong()
}

data class KeyValue(val key: String, val value: String)
