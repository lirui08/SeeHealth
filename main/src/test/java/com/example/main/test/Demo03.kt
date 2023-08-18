package com.example.main.test

import kotlinx.coroutines.*
import org.junit.Test

class Demo03 {

    @Test
    fun testmeth02()= runBlocking {
        supervisorScope {
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