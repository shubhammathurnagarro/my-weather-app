package com.ownagebyte.myweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ownagebyte.myweather.data.api.Response
import com.ownagebyte.myweather.data.model.WeatherForecastResponse
import com.ownagebyte.myweather.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class WeatherViewModel : ViewModel() {
    private val weatherRepository = WeatherRepository()
    private val _weatherResponseLiveData = MutableLiveData<Response<WeatherForecastResponse>>()
    val weatherResponseLiveData: LiveData<Response<WeatherForecastResponse>> =
        _weatherResponseLiveData

    fun getWeatherForecast(city: String) {
        viewModelScope.launch {
            try {
                val response: WeatherForecastResponse
                withContext(Dispatchers.IO) {
                    _weatherResponseLiveData.postValue(Response.Loading)
                    response = weatherRepository.getWeatherForecastForCity(city)
                }
                _weatherResponseLiveData.value = Response.Success(response)
            } catch (ex: HttpException) {
                ex.printStackTrace()
                _weatherResponseLiveData.value = Response.Error(ex)
            }
        }
    }
}
