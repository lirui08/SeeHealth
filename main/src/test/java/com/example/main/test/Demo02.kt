package com.example.main.test

import kotlinx.coroutines.*
import org.junit.Test

class Demo02 {

    /*@Test
    fun test07()= runBlocking {
        // 调用 runBlocking 函数 , 可以将 主线程 包装成 协程
        // 指定协程的启动模式为 CoroutineStart.UNDISPATCHED
        // 协程创建后 , 立即在当前的 函数调用栈 执行协程任务 ,
        // 直到遇到第一个挂起函数 , 才在子线程中执行挂起函数 ;
        val job = async ( context = Dispatchers.IO, start = CoroutineStart.UNDISPATCHED ) {
            // Dispatchers.IO 调度器是将协程调度到子线程执行
            // 但是如果 协程启动模式为 UNDISPATCHED , 则立刻在当前的主线程中执行协程
            // 此时打印出主线程 ,
            // 协程创建后 , 立即在当前的 函数调用栈 执行协程任务 , 因此会打印主线程
            println("协程开始执行 当前线程 : ${Thread.currentThread()}")
            // 遇到挂起函数会在子线程执行该挂起函数
            // 挂起函数都是耗时任务
            delay(2000)
            println("协程执行完毕")
            "Hello" // 返回一个字符串
        }
        //job.await()
    }

*/

    @Test
    fun testmeth02()= runBlocking {
        coroutineScope {
            launch {
                delay(1000)
                println("job1")
            }
            launch {
                delay(200)
                throw CancellationException()
                println("job2")
            }
        }
    }

}