package com.wangzy

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

fun test() {


    launch(CommonPool) {
        delay(2000L)    // 1
        println("Hello")
    }
    println("World")
    Thread.sleep(3000L) // 2
    // 在主线程中启动协程
    runBlocking<Unit> {
        println("T0")
        launch(CommonPool) {
            println("T1")
            delay(3000L)
            println("T2 Hello")
        }
        println("T3 World")
        delay(5000L)
        println("T4")
    }


}

fun main(args: Array<String>) {
    test()
}