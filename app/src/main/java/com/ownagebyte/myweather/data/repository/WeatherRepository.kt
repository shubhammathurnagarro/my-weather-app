package com.ownagebyte.myweather.data.repository

import com.ownagebyte.myweather.data.api.WeatherApiService
import com.ownagebyte.myweather.data.model.WeatherForecastResponse

class WeatherRepository(private val weatherApiService: WeatherApiService) {

    suspend fun getWeatherForecastForCity(city: String): WeatherForecastResponse {
        return weatherApiService.getForecastForCity(city)
    }
}
