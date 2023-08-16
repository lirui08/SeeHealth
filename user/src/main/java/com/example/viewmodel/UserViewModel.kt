package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repository.UserRepos
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody

//意图
sealed class UserIntent{
    data class sendCode(var phone:String):UserIntent()
    data class compareCode(var body: RequestBody):UserIntent()
    data class register(var body: RequestBody):UserIntent()
}
//状态
sealed class UserState{
    //注册
    data class RegisterSuccess(var code:String?):UserState()
    //校验验证码成功
    data class CompareCodeSuccess(var code:String?):UserState()
    //发送验证码成功
    data class SendCodeSuccess(var code:Int):UserState()
    data class Error(val msg:String):UserState()
    object Loading:UserState()
}

class UserViewModel:ViewModel() {

    val userRepos=UserRepos()

    //创建一个意图管道，
    val userchannel=Channel<UserIntent>(Channel.UNLIMITED)
    //可变状态数据流
    private val _userState=MutableStateFlow<UserState>(UserState.Loading)
    //可观察状态数据流
    val userState:StateFlow<UserState>
        get() = _userState

    init {
        handlerIntent()
    }

    fun handlerIntent(){
        viewModelScope.launch {
            //接收到意图
            userchannel.consumeAsFlow().collect{
                when(it){
                    is UserIntent.sendCode->
                        sendCode(it.phone)
                    is UserIntent.compareCode->
                        compareCode(it.body)
                    is UserIntent.register->
                        register(it.body)
                }
            }
        }
    }
    //注册
    fun register(body: RequestBody){
        viewModelScope.launch {
            var list=userRepos.register(body)
            if(list.code==0){
                //返回成功状态
                _userState.value=UserState.RegisterSuccess(list.data)
            }else{
                //返回失败状态
                _userState.value=UserState.Error(list.message)
            }
        }
    }
    //校验验证码
    fun compareCode(body: RequestBody){
        viewModelScope.launch {
            var list=userRepos.compareCode(body)
            if(list.code==0){
                //返回成功状态
                _userState.value=UserState.CompareCodeSuccess(list.data)
            }else{
                //返回失败状态
                _userState.value=UserState.Error(list.message)
            }
        }
    }
    //发送验证码
    fun sendCode(phone: String){
        viewModelScope.launch {
            var list=userRepos.sendCode(phone)
            if(list.code==0){
                //返回成功状态
                _userState.value=UserState.SendCodeSuccess(list.data!!)
            }else{
                //返回失败状态
                _userState.value=UserState.Error(list.message)
            }
        }
    }
}