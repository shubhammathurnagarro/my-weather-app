package com.ownagebyte.myweather.data.api

import com.ownagebyte.myweather.data.model.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("forecast?appid=$API_KEY&units=metric")
    suspend fun getForecastForCity(@Query("q") ciy: String): WeatherForecastResponse

    companion object {
        private const val API_KEY = "d2653946cb2fda423a36919e242992b3"

        val instance: WeatherApiService
            get() = RetrofitClient.createRetrofit().create(WeatherApiService::class.java)
    }
}
