package com.example.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.example.lib_common.ActivityPath
import com.example.user.databinding.ActivityRegisterBinding
import com.example.viewmodel.UserIntent
import com.example.viewmodel.UserState
import com.example.viewmodel.UserViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import java.lang.invoke.MethodType

class RegisterActivity : AppCompatActivity() {
    lateinit var db:ActivityRegisterBinding
    lateinit var vm:UserViewModel
    var code:Int=0
    var phone:String?=null
    var name:String?=null
    var pass:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(db.root)
        vm=ViewModelProvider(this).get(UserViewModel::class.java)


        lifecycleScope.launch {
            vm.userState.collect{
                when(it) {
                    is UserState.SendCodeSuccess -> {
                        code = it.code
                        ToastUtils.showLong(code.toString())
                    }
                    is UserState.CompareCodeSuccess->{
                        register()
                    }
                    is UserState.RegisterSuccess->{
                        ToastUtils.showLong("注册成功")
                        ARouter.getInstance().build(ActivityPath.PAGER_LOGIN).navigation()
                    }
                    is UserState.Error->ToastUtils.showLong(it.msg)
                }
            }
        }

        db.btnRegister.setOnClickListener {
            phone = db.etPhone.text.toString()
            name = db.etName.text.toString()
            pass = db.etPass.text.toString()
            val code = db.etCode.text.toString()
            if(TextUtils.isEmpty(phone)||TextUtils.isEmpty(name)||TextUtils.isEmpty(pass)||TextUtils.isEmpty(code)){
                ToastUtils.showLong("输入不能为空")
                return@setOnClickListener
            }
            if(!pass!!.matches(Regex("[0-9A-z&$#]{5,10}"))){
                ToastUtils.showLong("密码格式不匹配")
                return@setOnClickListener
            }
//            if(this.code!=code.toInt()){
//                ToastUtils.showLong("验证码不正确")
//                return@setOnClickListener
//            }
            //2、校验验证码
            lifecycleScope.launch {
                //发送验证码意图
                val map = hashMapOf("phone" to phone, "code" to code)
                val json = Gson().toJson(map)
                val body = RequestBody.create(MediaType.parse("application/json"), json)
                vm.userchannel.send(UserIntent.compareCode(body))
            }
            //3、注册

        }

        //1、点击按钮发送验证码
        db.btnSend.setOnClickListener(View.OnClickListener {
            val phone = db.etPhone.text.toString()
            if(TextUtils.isEmpty(phone)  || !phone.matches(Regex("1[0-9]{10}"))){
                ToastUtils.showLong("输入不能为空，或者格式不匹配")
                return@OnClickListener
            }
            lifecycleScope.launch {
                //发送验证码意图
                vm.userchannel.send(UserIntent.sendCode(phone))
            }
        })


    }

    fun register(){
        lifecycleScope.launch {
            //发送验证码意图
            val map = hashMapOf("phone" to phone, "username" to name,"password" to pass)
            val json = Gson().toJson(map)
            val body = RequestBody.create(MediaType.parse("application/json"), json)
            vm.userchannel.send(UserIntent.register(body))
        }
    }
}