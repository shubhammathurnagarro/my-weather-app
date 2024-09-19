package com.ownagebyte.myweather.data.repository

import com.ownagebyte.myweather.data.api.WeatherApiService
import com.ownagebyte.myweather.data.model.WeatherForecastResponse
import kotlinx.coroutines.delay

class WeatherRepository {
    private val weatherApiService = WeatherApiService.instance

    suspend fun getWeatherForecastForCity(city: String): WeatherForecastResponse {
        //delay(3000)
        return weatherApiService.getForecastForCity(city)
    }
}
