package com.ownagebyte.myweather.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.ownagebyte.myweather.R
import com.ownagebyte.myweather.data.api.Response
import com.ownagebyte.myweather.data.model.WeatherForecast
import com.ownagebyte.myweather.data.model.WeatherForecastResponse
import com.ownagebyte.myweather.databinding.ActivityMainBinding
import com.ownagebyte.myweather.ui.adapter.WeatherForecastListAdapter
import com.ownagebyte.myweather.utils.filterUpcomingDays
import com.ownagebyte.myweather.utils.format
import com.ownagebyte.myweather.viewmodel.WeatherViewModel
import kotlin.math.roundToInt

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<WeatherViewModel>()

    override fun setContentView() {
        ActivityMainBinding.inflate(layoutInflater).also {
            binding = it
            setContentView(binding.root)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val defaultCity = "Dubai"

        setupSearchView()
        binding.searchView.setQuery(defaultCity, true)

        viewModel.weatherResponseLiveData.observe(this) { response ->
            when (response) {
                is Response.Loading -> {
                    showProgressBar()
                }

                is Response.Error -> {
                    showErrorMessage(response.exception.message)
                }

                is Response.Success -> {
                    setupMainView(response.data)
                }
            }
        }
    }

    private fun setupMainView(weatherResponse: WeatherForecastResponse) {
        showProgressBar(false)
        binding.tvError.isVisible = false
        binding.mainView.isVisible = true

        if (weatherResponse.weatherList.isNotEmpty()) {
            setupCurrentTemperatureView(weatherResponse.weatherList.first())
            setupWeatherForecast(weatherResponse.weatherList)
        } else {
            showErrorMessage("Weather information missing from API")
        }
    }

    private fun setupCurrentTemperatureView(currentWeather: WeatherForecast) {
        currentWeather.main.let {
            binding.tvTemperature.text = it.temperature.format()
            binding.tvHighLow.text = it.getMaxMinTemperature()
            binding.tvFeelsLike.text =
                getString(R.string.feels_like_text, it.feelsLike.format())
            binding.tvHumidity.text = getString(R.string.humidity_text, it.humidity)
        }

        binding.tvWeather.text = currentWeather.weather.first().type
        binding.tvWindSpeed.text =
            getString(R.string.wind_speed_text, currentWeather.wind.speed.roundToInt().toString())
    }

    private fun setupWeatherForecast(weatherList: List<WeatherForecast>) {
        binding.lvForecast.layoutManager = LinearLayoutManager(this)
        binding.lvForecast.adapter = WeatherForecastListAdapter(weatherList.filterUpcomingDays())
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.getWeatherForecast(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun showProgressBar(show: Boolean = true) {
        binding.progressBar.isVisible = show
        if (show) {
            binding.tvError.isVisible = false
            binding.mainView.isVisible = false
        }
    }

    private fun showErrorMessage(message: String?) {
        showProgressBar(false)
        binding.tvError.isVisible = true
        binding.tvError.text = message ?: getString(R.string.error_text)
    }
}
