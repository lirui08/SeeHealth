package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_http.entity.GoosData
import com.example.repository.GoodsRepos
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

//意图
sealed class GoodsIntent{
    data class getGoods(val page:Int):GoodsIntent()
    object getAll:GoodsIntent()
}
//状态
sealed class GoodsState{
    data class Success(var list:MutableList<GoosData>):GoodsState()
    data class Error(val msg:String ):GoodsState()
    object Loading:GoodsState()
}

class GoodsViewModel:ViewModel() {


    val goodsRepos=GoodsRepos()

    //创建一个意图管道，
    val goodschannel=Channel<GoodsIntent>(Channel.UNLIMITED)
    //可变状态数据流
    private val _goodsState=MutableStateFlow<GoodsState>(GoodsState.Loading)
    //可观察状态数据流
    val goodsState:StateFlow<GoodsState>
        get() = _goodsState

    init {
        handlerIntent()
    }

    fun handlerIntent(){
        viewModelScope.launch {
            //接收到意图
            goodschannel.consumeAsFlow().collect{
                when(it){
                    is GoodsIntent.getGoods->
                        getGoods(it.page)
                }
            }
        }
    }
    //请求列表
    fun getGoods(page:Int){
        viewModelScope.launch {
            var list= goodsRepos.getGoods(page)
            if(list.code==200){
                //返回成功状态
                _goodsState.value=GoodsState.Success(list.data!!)
            }else{
                //返回失败状态
            // _goodsState.value=GoodsState.Error(list.msg)
            }
        }
    }
}