package com.ownagebyte.myweather.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ownagebyte.myweather.data.model.WeatherSummary
import com.ownagebyte.myweather.databinding.ItemRecentSearchBinding
import com.ownagebyte.myweather.utils.loadNetworkImage

class RecentSearchListAdapter(
    private val weatherList: List<WeatherSummary>,
    private val onItemClick: (String) -> Unit,
) : RecyclerView.Adapter<RecentSearchListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemRecentSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick,
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(weatherList[position])

    override fun getItemCount(): Int = weatherList.size

    class ViewHolder(
        private val binding: ItemRecentSearchBinding,
        private val onItemClick: (String) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: WeatherSummary) {
            binding.tvCityName.text = weather.cityName
            binding.ivWeatherIcon.loadNetworkImage(weather.iconUrl)
            binding.tvWeather.text = weather.weather
            binding.tvTemperature.text = weather.temperature
            binding.tvHighLow.text = weather.tempMaxMin

            binding.root.setOnClickListener { onItemClick.invoke(weather.cityName) }
        }
    }
}
