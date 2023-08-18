package com.example.lib_http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class BasicParamsInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url()
        val url = originalHttpUrl.newBuilder().apply {
            addQueryParameter("udid","123456")
        }.build()
        val request = originalRequest.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}