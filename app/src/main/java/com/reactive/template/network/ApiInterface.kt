package com.reactive.template.network

import com.reactive.template.network.models.Token
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    // region Auth start
    @POST("account/register")
    suspend fun register(): Token
    // region Auth end

    // region Vehicles Api start
    @POST("Vehicles/getLastData")
    suspend fun getLastData(
        @Query("fleetcomplete-api-key") apiKey: String,
        @Query("json") json: Boolean = true
    ): Token

    @POST("Vehicles/getRawData")
    suspend fun getRawData(
        @Query("begTimestamp") begTimestamp: String,
        @Query("endTimestamp") endTimestamp: String,
        @Query("objectId") objectId: String,
        @Query("key") apiKey: String,
        @Query("json") json: Boolean = true
    ): Token
    // region Vehicles Api end
}
