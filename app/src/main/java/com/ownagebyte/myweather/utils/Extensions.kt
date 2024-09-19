package com.ownagebyte.myweather.utils

import com.ownagebyte.myweather.data.model.WeatherForecast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

fun Float.format(): String = "${roundToInt()}°"

fun Long.formatToDate(): String {
    val sdf = SimpleDateFormat("EEEE dd MMM", Locale.getDefault())
    return sdf.format(Date(this * 1000))
}

fun List<WeatherForecast>.filterUpcomingDays(): List<WeatherForecast> {
    var lastDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    val calendar = Calendar.getInstance()
    return filter {
        calendar.time = Date(it.date * 1000)
        val currDate = calendar.get(Calendar.DAY_OF_MONTH)
        currDate != lastDate.also { lastDate = currDate }
    }
}
