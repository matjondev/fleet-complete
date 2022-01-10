package com.reactive.template.network

data class BaseError(
    var code: String? = null,
    var message: String? = null,
    var name: String? = null
)

open class BaseResponse {
    var success: Boolean = false
    var exception: BaseError? = null
    var count: Int? = 0

    fun isSuccess() = success
}


data class TokenRequest(
    val accessToken: String,
    val id: Int? = null
)
