package com.example.lib_http

data class ResponseData<T> (
    val code:Int,
    val msg:String,
    val data:T?
)