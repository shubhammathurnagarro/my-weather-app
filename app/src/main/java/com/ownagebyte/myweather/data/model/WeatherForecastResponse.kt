package com.ownagebyte.myweather.data.model

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("cod") val code: Int,
    @SerializedName("cnt") val count: Int,
    @SerializedName("list") val weatherList: List<WeatherForecast>,
    val city: CityData,
)

data class CityData(
    val id: Int,
    val name: String,
    val sunrise: Long,
    val sunset: Long,
)
