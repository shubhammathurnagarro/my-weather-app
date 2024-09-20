package com.ownagebyte.myweather.data.model

import com.google.gson.annotations.SerializedName
import com.ownagebyte.myweather.utils.format

data class WeatherForecastResponse(
    @SerializedName("cod") val code: Int,
    @SerializedName("cnt") val count: Int,
    @SerializedName("list") val weatherList: List<WeatherForecast>,
    val city: CityData,
) {
    fun getWeatherSummary(): WeatherSummary {
        val weather = weatherList.firstOrNull()
        return WeatherSummary(
            city.name,
            weather?.main?.temperature?.format().orEmpty(),
            weather?.main?.getMaxMinTemperatureShort().orEmpty(),
            weather?.weather?.firstOrNull()?.weatherType.orEmpty(),
            weather?.weather?.firstOrNull()?.iconUrl.orEmpty(),
        )
    }
}

data class CityData(
    val id: Int,
    val name: String,
    val sunrise: Long,
    val sunset: Long,
)
