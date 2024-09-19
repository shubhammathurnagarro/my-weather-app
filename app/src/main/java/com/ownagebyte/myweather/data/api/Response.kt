package com.ownagebyte.myweather.data.api

sealed class Response<out T> {
    data object Loading : Response<Nothing>()

    class Error(val exception: Exception) : Response<Nothing>()

    class Success<T>(val data: T) : Response<T>()
}
