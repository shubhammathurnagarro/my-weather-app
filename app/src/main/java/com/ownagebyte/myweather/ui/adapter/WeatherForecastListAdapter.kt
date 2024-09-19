package com.ownagebyte.myweather.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ownagebyte.myweather.R
import com.ownagebyte.myweather.data.model.WeatherForecast
import com.ownagebyte.myweather.databinding.ItemForecastListBinding
import com.ownagebyte.myweather.utils.format
import com.ownagebyte.myweather.utils.formatToDate

class WeatherForecastListAdapter(private val weatherList: List<WeatherForecast>) :
    RecyclerView.Adapter<WeatherForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemForecastListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int = weatherList.size

    class ViewHolder(private val binding: ItemForecastListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: WeatherForecast) {
            binding.tvDate.text = weather.date.formatToDate()

            binding.tvWeather.text = weather.weather.first().type

            weather.main.let {
                binding.tvHighLow.text = binding.root.context.getString(
                    R.string.high_low_text_forecast,
                    it.tempMax.format(),
                    it.tempMin.format()
                )
            }

        }
    }
}
