package com.ownagebyte.myweather.data.model

data class WeatherSummary(
    val cityName: String,
    val temperature: String,
    val tempMaxMin: String,
    val weather: String,
    val iconUrl: String,
)
