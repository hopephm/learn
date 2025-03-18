package kr.hope.learn.coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HelloWorldTest {
    @Test
    fun helloWorldTest() = runTest {
        // given
        val list: MutableList<String> = mutableListOf()

        // when
        val job = launch {
            delay(1000L)
            list.add("World!")
        }
        list.add("Hello,")
        job.join()

        // then
        assertThat(list.size).isEqualTo(2)
        assertThat(list[0]).isEqualTo("Hello,")
        assertThat(list[1]).isEqualTo("World!")

        Unit
    }
}