package com.reactive.template.utils.preferences

import android.content.SharedPreferences
import com.google.gson.Gson
import com.reactive.template.utils.preferences.PreferenceHelper.get
import com.reactive.template.utils.preferences.PreferenceHelper.set

class SharedManager(
    private val preferences: SharedPreferences,
    private val gson: Gson
) {

    companion object {
        const val TOKEN = "TOKEN"
    }

    var token: String
        get() = preferences[TOKEN, ""]
        set(value) {
            preferences[TOKEN] = value
        }

    fun deleteAll() {
        preferences.edit().clear().apply()
    }
}
