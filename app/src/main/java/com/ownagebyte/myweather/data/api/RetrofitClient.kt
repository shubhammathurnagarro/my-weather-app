package com.ownagebyte.myweather.data.api

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    fun createRetrofit(context: Context): Retrofit {
        val cache = Cache(context.cacheDir, 5 * 1024 * 1024)
        val httpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .addNetworkInterceptor(CacheInterceptor())
            .addInterceptor(ForceAppCacheInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }
}
