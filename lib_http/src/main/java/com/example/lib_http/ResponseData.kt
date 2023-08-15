package com.example.lib_http

data class ResponseData<T> (
    val code:Int,
    val message:String,
    val data:T?
)