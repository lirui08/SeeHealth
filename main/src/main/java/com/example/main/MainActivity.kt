package com.example.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_common.ActivityPath
import kotlinx.coroutines.*

@Route(path = ActivityPath.PAGER_MAIN)
class MainActivity : AppCompatActivity() {
    var mainScope= MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*mainScope.launch {
            delay(5000)
            println("mainScope执行")
        }*/

        runBlocking {
            supervisorScope {
                // 该 coroutineScope 协程作用域 将 子协程 job0 和 job1 包裹起来
                // coroutineScope 作用域需要等待 两个子协程执行完毕 , 该作用域才算执行完毕

                // coroutineScope 函数 构建的 协程作用域 ,
                // 如果有一个 子协程 执行失败 , 则其它 所有的子协程会被取消 ;

                val job0 = launch {
                    println("job0 协程开始执行")
                    delay(2000)
                    println("job0 协程执行完毕")
                }

                val job1 = async {
                    println("job1 协程开始执行")
                    delay(1000)

                    // 抛出异常 , job1 执行取消
                    println("job1 协程 抛出异常取消执行")
                    throw java.lang.IllegalArgumentException()

                    println("job1 协程执行完毕")
                    "Hello" // 返回一个字符串
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
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