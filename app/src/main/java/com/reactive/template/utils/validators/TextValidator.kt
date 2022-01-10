package com.reactive.template.utils.validators

import android.text.TextUtils
import android.util.Patterns

object TextValidator {
    fun isEmail(email: String) : Boolean {
        if (!TextUtils.isEmpty(email)) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
        return false
    }
}