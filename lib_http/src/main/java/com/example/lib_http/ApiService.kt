package com.example.lib_http

import com.example.lib_http.entity.GoosData
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("goods/info?category_id=0&pageSize=10")
    suspend fun getGoods(@Query("currentPage") currentPage:Int):ResponseData<MutableList<GoosData>>

    @GET("/api/sms/sendrcode")
    suspend fun sendCode(@Query("phone")phone:String):ResponseData<Int>

    @POST("/api/sms/checkrcode")
    suspend fun compareCode(@Body requestBody: RequestBody):ResponseData<String?>

    @POST("/api/user/register")
    suspend fun register(@Body requestBody: RequestBody):ResponseData<String?>

}