package com.ownagebyte.myweather.data.api

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class ForceAppCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            chain.proceed(chain.request())
        } catch (ex: Exception) {
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            )
        }
    }
}
