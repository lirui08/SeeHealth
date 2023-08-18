package com.example.lib_http

import com.example.lib_http.interceptor.*
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitManager {
    fun createCache(): Cache {
        var file = File("/sdcard/Music")
        return Cache(file,10*1024*1024)
    }

    @JvmField
    var retrofit: Retrofit?=null
    fun getRetrofit():Retrofit{
        if(retrofit==null){
            synchronized(RetrofitManager::class.java){
                if(retrofit==null){
                    val client = OkHttpClient.Builder()
                        .cache(createCache())//把请求内容缓存
                        .connectTimeout(5, TimeUnit.SECONDS)
//                        .retryOnConnectionFailure(true)
                        .addInterceptor(RetryInterceptor(3))
                        .addInterceptor(BasicParamsInterceptor())
                        .addInterceptor(HeaderInterceptor())
                        .addNetworkInterceptor(ResponseCacheInterceptor())
                        .addInterceptor(RequestCacheInterceptor())
                        .addInterceptor(object : Interceptor {
                            override fun intercept(chain: Interceptor.Chain): Response {
                                val response = chain.proceed(chain.request())
                                return response
                            }
                        })
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build()
                    retrofit = Retrofit.Builder()
                        .baseUrl(
                            "http://43.143.146.165:7777/"
//                                    "http://43.143.157.87:8686/"
                        )
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