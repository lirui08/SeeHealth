package com.example.lib_http.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class HeaderInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            header("model", "Android")
            header("If-Modified-Since", "${Date()}")
            header("User-Agent", System.getProperty("http.agent") ?: "unknown")
        }.build()
        return chain.proceed(request)
    }
}