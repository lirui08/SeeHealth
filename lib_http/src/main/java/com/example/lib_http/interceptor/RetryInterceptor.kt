package com.example.lib_http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class RetryInterceptor(var maxRetry:Int):Interceptor {
    private var  retryNum = 0 //已经重连多少次
    override fun intercept(chain: Interceptor.Chain): Response {
        val  request = chain.request()
        var response = chain.proceed(request)
        while (!response.isSuccessful && retryNum < maxRetry){
            retryNum++
            response = chain.proceed(request)
        }
        return response
    }
}