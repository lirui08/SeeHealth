package com.example.lib_http.interceptor

import com.blankj.utilcode.util.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class RequestCacheInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        //拦截请求头 判断哪些接口配置上了缓存（该配置是在retrofit上配置）
        var cacheControl = request.cacheControl().toString()
        //如果没有配置过 那么就是走正常的访问，这里直接return回去了，并没有对请求做处理
        if(cacheControl.isEmpty()){
            return chain.proceed(request)
        }
        //如果没有网络的情况下，并且配置了缓存，那么我们就设置强制读取缓存，没有读到就抛异常，但是并不会去服务器请求数据
        if(!NetworkUtils.isConnected()){
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)//强制从缓存读取
                // .cacheControl(CacheControl.FORCE_NETWORK)//强制从网络读取,默认是网络读取
                .build();
        }
        return chain.proceed(request);

    }
}