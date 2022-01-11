package com.reactive.template.network

data class BaseError(
    var code: String? = null,
    var message: String? = null,
    var name: String? = null
)

open class BaseResponse {
    var status:Int = 0
    var meta:Any? = null
    var errormessage: String? = null
    var count: Int? = 0

    fun isSuccess() = errormessage == null
}


data class TokenRequest(
    val accessToken: String,
    val id: Int? = null
)
