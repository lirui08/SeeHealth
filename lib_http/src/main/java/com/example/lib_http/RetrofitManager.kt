package com.example.lib_http

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {

    @JvmField
    var retrofit: Retrofit?=null
    fun getRetrofit():Retrofit{
        if(retrofit==null){
            synchronized(RetrofitManager::class.java){
                if(retrofit==null){
                    val client = OkHttpClient.Builder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .addInterceptor(object : Interceptor {
                            override fun intercept(chain: Interceptor.Chain): Response {
                                val response = chain.proceed(chain.request())
                                return response
                            }
                        })
                        .build()
                    retrofit = Retrofit.Builder()
                        .baseUrl("http://43.143.146.165:7777/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
        }
        return retrofit!!
    }

    fun getApiService():ApiService{
        val apiService = getRetrofit()?.create(ApiService::class.java)
        return apiService!!
    }

}