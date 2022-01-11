package com.reactive.template.network.models

import com.reactive.template.network.BaseResponse


data class RawDataResp(val response:List<RawData>):BaseResponse()
data class RawData(
    val Din1: Any,
    val Din2: Any,
    val Din3: Any,
    val Direction: Double,
    val Distance: Double,
    val DriverId: Any,
    val EngineStatus: Any,
    val EventType_dec: String,
    val GPSState: String,
    val Latitude: Double,
    val Longitude: Double,
    val Power: Double,
    val ServerGenerated: Any,
    val Speed: Any,
    val SplitSegment: Any,
    val timestamp: String
)
