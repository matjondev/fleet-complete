package com.reactive.template.network

import com.reactive.template.network.models.LastDataResp
import com.reactive.template.network.models.RawData
import com.reactive.template.network.models.RawDataResp
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
        @Query("key") apiKey: String,
        @Query("json") json: Boolean = true
    ): LastDataResp

    @POST("Vehicles/getRawData")
    suspend fun getRawData(
        @Query("begTimestamp") begTimestamp: String,
        @Query("endTimestamp") endTimestamp: String,
        @Query("objectId") objectId: String,
        @Query("key") apiKey: String,
        @Query("json") json: Boolean = true
    ): RawDataResp
    // region Vehicles Api end
}
