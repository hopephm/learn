package kr.hope.learn.coroutine

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() = coroutineScope {
    val job = launch {
        delay(1000L)
        println("World!")
    }
    print("Hello,")
    job.join()
}