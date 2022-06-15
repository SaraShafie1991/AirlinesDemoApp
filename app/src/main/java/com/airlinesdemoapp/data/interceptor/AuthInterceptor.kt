package com.airlinesdemoapp.data.interceptor

import android.content.Context
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val appContext : Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalUrl: HttpUrl = original.url()
        val requestBuilder: Request.Builder = original.newBuilder().url(originalUrl)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}