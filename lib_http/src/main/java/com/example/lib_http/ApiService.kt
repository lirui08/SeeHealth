package com.example.lib_http

import com.example.lib_http.entity.GoosData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("goods/info?category_id=0&pageSize=10")
    suspend fun getGoods(@Query("currentPage") currentPage:Int):ResponseData<MutableList<GoosData>>
}