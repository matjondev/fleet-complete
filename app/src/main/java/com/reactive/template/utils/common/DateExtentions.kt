package com.reactive.template.utils.common

import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

fun calculateDate(start:String,end:String):String{
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val startDate = simpleDateFormat.parse(start)
    val endDate = simpleDateFormat.parse(end)
    val resultTime = abs(endDate.time - startDate.time)

    val resultDate = Date(resultTime)
    val resultSimpleDateFormat = SimpleDateFormat("HH'h' mm'm' ss's'")
    return resultSimpleDateFormat.format(resultDate)
}

fun Date.getFormatTime(): String {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    return simpleDateFormat.format(this)
}

fun Date.formatToRequest():String{
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    return simpleDateFormat.format(this)
}

public fun Date.today():Date = Date(System.currentTimeMillis())
fun Date.yesterday():Date = Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24))

