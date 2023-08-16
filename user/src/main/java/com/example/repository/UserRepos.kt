package com.example.repository

import com.example.lib_http.ResponseData
import com.example.lib_http.RetrofitManager
import com.example.lib_http.entity.GoosData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody

class UserRepos {
    val apiService by lazy { RetrofitManager.getApiService() }

    suspend fun sendCode(phone:String): ResponseData<Int>{
        return withContext(Dispatchers.IO){
            apiService.sendCode(phone)
        }
    }

    suspend fun compareCode(body:RequestBody): ResponseData<String?>{
        return withContext(Dispatchers.IO){
            apiService.compareCode(body)
        }
    }


    suspend fun register(body:RequestBody): ResponseData<String?>{
        return withContext(Dispatchers.IO){
            apiService.register(body)
        }
    }
}