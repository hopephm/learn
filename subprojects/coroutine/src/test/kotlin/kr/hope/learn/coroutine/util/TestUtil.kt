package kr.hope.learn.coroutine.util

object TestUtil {
    fun <T> generate(times: Int, action: () -> T): List<T> {
        return (0 until times).map { action() }
    }
}