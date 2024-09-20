package com.ownagebyte.myweather.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.ownagebyte.myweather.data.model.WeatherSummary

object SharedPreferences {
    private const val SHARED_PREFS_NAME = "shared_prefs"
    private const val LAST_CITY_NAME = "last_city_name"
    private const val RECENT_SEARCHES = "recent_searches"

    private fun getSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    fun getLastSearchedCity(context: Context): String {
        val sharedPres = getSharedPreferences(context)
        return sharedPres.getString(LAST_CITY_NAME, "Dubai").orEmpty()
    }

    fun setLastSearchedCity(context: Context, city: String, weatherSummary: WeatherSummary) {
        getSharedPreferences(context)
            .edit()
            .putString(LAST_CITY_NAME, city)
            .putString(city, Gson().toJson(weatherSummary))
            .apply()
    }

    private fun getRecentSearchedCities(context: Context): List<String> {
        val sharedPres = getSharedPreferences(context)
        return sharedPres.getString(RECENT_SEARCHES, "").orEmpty().split(",").filter { it.isNotEmpty() }
    }

    fun addRecentSearchedCity(context: Context, lastSearchedCity: String, currentCity: String) {
        val recentList = getRecentSearchedCities(context).toMutableList()

        if (recentList.contains(currentCity)) recentList.remove(currentCity)
        if (recentList.contains(lastSearchedCity)) recentList.remove(lastSearchedCity)
        recentList.add(lastSearchedCity)

        getSharedPreferences(context)
            .edit()
            .putString(RECENT_SEARCHES, recentList.joinToString(","))
            .apply()
    }

    fun getRecentSearchedCitiesData(context: Context): List<WeatherSummary> {
        val sharedPres = getSharedPreferences(context)
        val gson = Gson()

        val summaryList = mutableListOf<WeatherSummary>()
        getRecentSearchedCities(context).forEach { city ->
            summaryList.add(
                gson.fromJson(
                    sharedPres.getString(city, ""),
                    WeatherSummary::class.java
                )
            )
        }

        return summaryList.apply { reverse() }
    }
}
