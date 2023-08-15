package com.example.repository

import com.example.lib_http.ResponseData
import com.example.lib_http.RetrofitManager
import com.example.lib_http.entity.GoosData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoodsRepos {
    val apiService by lazy { RetrofitManager.getApiService() }

    suspend fun getGoods(currentPage:Int): ResponseData<MutableList<GoosData>>{
        return withContext(Dispatchers.IO){
            apiService.getGoods(currentPage)
        }
    }
}