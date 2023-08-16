package com.example.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.ActivityPath
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Route(path = ActivityPath.PAGER_MAIN)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

    fun mymap(){
        val map = mapOf("name" to "张三", "sex" to "男")
        val map1 = mapOf(Pair("name", "张三"), Pair("sex", "男"))
        val map2 = hashMapOf("name" to "张三", "sex" to "男")
        map2.put("age",18.toString())
        map2+= "pass" to ("666")

        map2.remove("age")
        map2-="name"

        //修改或者添加
        map2["sex2"]="女"

        println(map.get("n2me2"))

        map.getOrDefault("names","无值")
        map.getOrElse("namess"){"无值"}


        map.forEach { key, value ->
            println("$key,$value")
        }

        map.forEach {
            println("${it.key},${it.value}")
        }

        for (item in map){
            println("${item.key},${item.value}")
        }
    }

    fun start(view: View) {
        /*GlobalScope.launch(Dispatchers.Main) {
            delay(5000)
            Log.i("===","delay:${Thread.currentThread().name}")
        }*/
        Thread.sleep(5000)
        Log.i("===","sleep:${Thread.currentThread().name}")
    }
}