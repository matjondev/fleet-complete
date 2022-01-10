package com.reactive.template.network.models

import com.reactive.template.network.BaseResponse

data class Token(
    val access_token: String,
    val token_type: String,
    val unique_id: String
) : BaseResponse()
