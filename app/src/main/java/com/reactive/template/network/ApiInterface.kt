package com.reactive.template.network

import com.reactive.template.network.models.Token
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    // region Auth start
    @POST("account/register")
    suspend fun register(): Token
    // region Auth end
}
