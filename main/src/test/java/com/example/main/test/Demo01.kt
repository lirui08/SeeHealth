package com.example.main.test


import kotlinx.coroutines.*
import org.junit.Test
import kotlin.coroutines.*
import kotlin.system.measureTimeMillis

class Demo01 {

    @Test
    fun test01(){
        var con= suspend {
            10
        }.createCoroutine(object :Continuation<Int>{
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                println("result:${result}")
            }

        })
        con.resume(Unit)
    }

    @Test
    fun test02()= runBlocking {
        var job1=launch {
            println("launch执行")
            "job1"
        }
        var job2=async {
            println("async执行")
            "job2"
        }
        println(job2.await())
    }

    @Test
    fun test03()= runBlocking {

        var time=measureTimeMillis {
            var job1=async {
                delay(1000)
                2
            }
            var job2=async {
                delay(1000)
                8
            }
            println(job1.await()+job2.await())
        }
        println("time:${time}")

    }

    @Test
    fun test04()= runBlocking {

        var time=measureTimeMillis {
            var job3= launch {

            }
            job3.join()
            var job1=async {
                delay(3000)
                println("job1")
                2
            }
            job1.await()
            var job2=async {
                delay(3000)
                println("job2")
                8
            }
        }
        println("time:${time}")

    }


    @Test
    fun test05()=runBlocking {
        // 调用 runBlocking 函数 , 可以将 主线程 包装成 协程

        // 指定协程的启动模式为 CoroutineStart.DEFAULT
        // 默认的 协程启动模式 , 协程创建后 , 马上开始调度执行 ,
        // 如果在调度前取消协程 , 则进入 取消响应 状态 ;
        val job = launch (start = CoroutineStart.ATOMIC) {
            println( "协程开始执行")
            delay(2000)
            println("协程执行完毕")
        }
//        delay(1000)
        // 取消协程
        job.cancel()
    }

    @Test
    fun test06()=runBlocking {
        // 调用 runBlocking 函数 , 可以将 主线程 包装成 协程
        // 指定协程的启动模式为 CoroutineStart.LAZY
        // 协程创建后 , 不会马上开始调度执行 ,
        // 只有 主动调用协程的 start , join , await 方法 时 , 才开始调度执行协程 ,
        // 如果在 调度之前取消协程 , 该协程直接报异常 进入异常响应状态 ;
        val job = async (start = CoroutineStart.LAZY) {
            println("协程开始执行")
            delay(2000)
            println("协程执行完毕")
            "Hello" // 返回一个字符串
        }
        delay(1000)

        // 取消协程 , 在调度之前取消 , 协程直接进入异常响应状态
//        job.cancel()

        // 执行下面两个方法中的任意一个方法 ,
        // 启动执行协程
        job.start()
        // 获取协程返回值
        job.await()
    }


    @Test
    fun test07()=runBlocking {
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
}