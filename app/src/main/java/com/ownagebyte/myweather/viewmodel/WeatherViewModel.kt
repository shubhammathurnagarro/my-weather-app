package com.ownagebyte.myweather.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ownagebyte.myweather.data.api.Response
import com.ownagebyte.myweather.data.api.WeatherApiService
import com.ownagebyte.myweather.data.model.WeatherForecastResponse
import com.ownagebyte.myweather.data.model.WeatherSummary
import com.ownagebyte.myweather.data.repository.SharedPreferences
import com.ownagebyte.myweather.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val weatherRepository = WeatherRepository(WeatherApiService.getInstance(getApplication()))

    private val _lastSearchedCityLiveData = MutableLiveData<String>()
    val lastSearchedCityLiveData: LiveData<String> = _lastSearchedCityLiveData

    private val _weatherResponseLiveData = MutableLiveData<Response<WeatherForecastResponse>>()
    val weatherResponseLiveData: LiveData<Response<WeatherForecastResponse>> = _weatherResponseLiveData

    private val _recentSearchedCitiesLiveData = MutableLiveData<Response<List<WeatherSummary>>>()
    val recentSearchedCitiesLiveData: LiveData<Response<List<WeatherSummary>>> = _recentSearchedCitiesLiveData

    fun initWeatherData() {
        viewModelScope.launch {
            val city = SharedPreferences.getLastSearchedCity(getApplication())
            _lastSearchedCityLiveData.value = city
        }
    }

    fun getWeatherForecast(city: String) {
        viewModelScope.launch {
            try {
                val response: WeatherForecastResponse
                _weatherResponseLiveData.value = Response.Loading

                withContext(Dispatchers.IO) { response = weatherRepository.getWeatherForecastForCity(city) }

                _weatherResponseLiveData.value = Response.Success(response)
                updateRecentSearches(city, response.getWeatherSummary())
            } catch (ex: HttpException) {
                ex.printStackTrace()

                var exception: Exception? = null
                if (ex.code() == 504) {
                    exception = Exception("Gateway timeout! Please check your internet connection if problem persists.")
                } else if (ex.code() == 404) {
                    exception = Exception("No data found. Try another city.")
                }
                _weatherResponseLiveData.value = Response.Error(exception ?: ex)
            }
        }
    }

    private fun updateRecentSearches(currentCity: String, weatherSummary: WeatherSummary) {
        val lastSearched = SharedPreferences.getLastSearchedCity(getApplication())
        SharedPreferences.setLastSearchedCity(getApplication(), currentCity, weatherSummary)

        if (currentCity != lastSearched) {
            SharedPreferences.addRecentSearchedCity(getApplication(), lastSearched, currentCity)
        }
        getRecentSearches()
    }

    private fun getRecentSearches() {
        viewModelScope.launch {
            try {
                val response: List<WeatherSummary>
                _recentSearchedCitiesLiveData.value = Response.Loading

                withContext(Dispatchers.IO) { response = SharedPreferences.getRecentSearchedCitiesData(getApplication()) }

                _recentSearchedCitiesLiveData.value = Response.Success(response)
            } catch (ex: Exception) {
                ex.printStackTrace()
                _recentSearchedCitiesLiveData.value = Response.Error(ex)
            }
        }
    }
}
