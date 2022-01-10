package com.reactive.template.utils.extensions

import android.widget.ImageView
import coil.imageLoader
import coil.request.ImageRequest
import com.reactive.template.R

fun ImageView.loadImage(src: Any?) {
    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .data(src)
        .target(this)
        .build()

    context.imageLoader.enqueue(request)
}
