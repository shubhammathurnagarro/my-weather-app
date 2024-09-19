package com.ownagebyte.myweather.data.model

import com.google.gson.annotations.SerializedName
import com.ownagebyte.myweather.utils.format

// icon url http://openweathermap.org/img/w/10d.png
data class WeatherForecast(
    @SerializedName("dt") val date: Long,
    val main: MainData,
    val weather: List<WeatherData>,
    val wind: WindData,
)

data class MainData(
    @SerializedName("temp") val temperature: Float,
    @SerializedName("feels_like") val feelsLike: Float,
    @SerializedName("temp_min") val tempMin: Float,
    @SerializedName("temp_max") val tempMax: Float,
    val humidity: Int,
) {
    fun getMaxMinTemperature(): String {
        return "High: ${tempMax.format()} | Low: ${tempMin.format()}"
    }
}

data class WeatherData(
    @SerializedName("main") val type: String,
    val icon: String,
)

data class WindData(
    val speed: Float,
    @SerializedName("deg") val degree: Float
)
