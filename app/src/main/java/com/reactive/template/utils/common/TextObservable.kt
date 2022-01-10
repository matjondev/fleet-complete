package com.reactive.template.utils.common

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.reactive.template.utils.extensions.disable
import com.reactive.template.utils.extensions.enable

class TextObservable(val view: View, val action: (String) -> Unit) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        if (s.toString().isNotEmpty()) {
            view.enable()
            action.invoke(s.toString())
        } else {
            view.disable()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

}