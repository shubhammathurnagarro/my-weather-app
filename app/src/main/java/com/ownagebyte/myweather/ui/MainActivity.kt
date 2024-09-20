package com.ownagebyte.myweather.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ownagebyte.myweather.R
import com.ownagebyte.myweather.data.api.Response
import com.ownagebyte.myweather.data.model.WeatherForecast
import com.ownagebyte.myweather.data.model.WeatherForecastResponse
import com.ownagebyte.myweather.data.model.WeatherSummary
import com.ownagebyte.myweather.databinding.ActivityMainBinding
import com.ownagebyte.myweather.ui.adapter.RecentSearchListAdapter
import com.ownagebyte.myweather.ui.adapter.WeatherForecastListAdapter
import com.ownagebyte.myweather.utils.HorizontalSpaceDecoration
import com.ownagebyte.myweather.utils.filterUpcomingDays
import com.ownagebyte.myweather.utils.format
import com.ownagebyte.myweather.utils.loadNetworkImage
import com.ownagebyte.myweather.viewmodel.WeatherViewModel
import kotlin.math.roundToInt

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<WeatherViewModel>()

    private val weatherForecastList: MutableList<WeatherForecast> = mutableListOf()
    private val recentSearchesList: MutableList<WeatherSummary> = mutableListOf()

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

        initViews()
        initObservers()
        viewModel.initWeatherData()
    }

    private fun initViews() {
        setupWeatherForecastView()
        setupRecentSearchesView()
        setupSearchView()
    }

    private fun setupWeatherForecastView() {
        binding.rvForecast.layoutManager = LinearLayoutManager(this)
        binding.rvForecast.adapter = WeatherForecastListAdapter(weatherForecastList)
    }

    private fun updateWeatherForecastView(forecastList: List<WeatherForecast>) {
        weatherForecastList.clear()
        weatherForecastList.addAll(forecastList.filterUpcomingDays())
        binding.rvForecast.adapter?.notifyDataSetChanged()
    }

    private fun setupRecentSearchesView() {
        binding.rvRecentSearches.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvRecentSearches.addItemDecoration(HorizontalSpaceDecoration(resources.getDimension(R.dimen.margin_horizontal).toInt()))
        binding.rvRecentSearches.adapter = RecentSearchListAdapter(recentSearchesList) { city ->
            binding.searchView.setQuery(city, true)
        }
    }

    private fun updateRecentSearchesView(recentSearches: List<WeatherSummary>) {
        if (recentSearches.isNotEmpty()) {
            binding.groupRecentSearches.isVisible = true
            recentSearchesList.clear()
            recentSearchesList.addAll(recentSearches)
            binding.rvRecentSearches.adapter?.notifyDataSetChanged()
            binding.rvRecentSearches.scrollToPosition(0)
        } else {
            binding.groupRecentSearches.isVisible = false
        }
    }

    private fun initObservers() {
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

        viewModel.recentSearchedCitiesLiveData.observe(this) { response ->
            when (response) {
                is Response.Loading, is Response.Error -> {
                    binding.groupRecentSearches.isVisible = false
                }

                is Response.Success -> {
                    updateRecentSearchesView(response.data)
                }
            }
        }

        viewModel.lastSearchedCityLiveData.observe(this) { lastSearchedCity ->
            binding.searchView.setQuery(lastSearchedCity, true)
        }
    }

    private fun setupMainView(weatherResponse: WeatherForecastResponse) {
        showProgressBar(false)
        binding.tvError.isVisible = false
        binding.mainView.isVisible = true

        if (weatherResponse.weatherList.isNotEmpty()) {
            setupCurrentTemperatureView(weatherResponse.weatherList.first())
            updateWeatherForecastView(weatherResponse.weatherList)
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

        currentWeather.weather.firstOrNull()?.let { weather ->
            binding.ivWeatherIcon.loadNetworkImage(weather.iconUrl)
            binding.tvWeather.text = weather.weatherType
        }

        binding.tvWindSpeed.text =
            getString(R.string.wind_speed_text, currentWeather.wind.speed.roundToInt().toString())
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.trim()?.let { viewModel.getWeatherForecast(it) }
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
