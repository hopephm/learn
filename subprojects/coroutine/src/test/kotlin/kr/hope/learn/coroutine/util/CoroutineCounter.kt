package kr.hope.learn.coroutine.util

import kotlinx.coroutines.sync.Mutex

class CoroutineCounter {
    private val mutex = Mutex()
    private var count = 0

    suspend fun increase() {
        mutex.lock()
        count++
        mutex.unlock()
    }

    suspend fun get(): Int {
        mutex.lock()
        return count.apply {
            mutex.unlock()
        }
    }
}