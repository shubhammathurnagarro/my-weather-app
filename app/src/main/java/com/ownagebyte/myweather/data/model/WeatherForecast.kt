package com.ownagebyte.myweather.data.model

import com.google.gson.annotations.SerializedName
import com.ownagebyte.myweather.utils.format

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

    fun getMaxMinTemperatureShort(): String {
        return "${tempMax.format()}/${tempMin.format()}"
    }
}

data class WeatherData(
    val main: String,
    val icon: String,
) {
    val weatherType: String
        get() = if (main == "Clouds") "Cloudy" else main

    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${icon.replace("n", "d")}@2x.png"
}

data class WindData(
    val speed: Float,
    @SerializedName("deg") val degree: Float
)
