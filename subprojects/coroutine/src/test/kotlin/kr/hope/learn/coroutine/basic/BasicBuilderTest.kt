package kr.hope.learn.coroutine.basic

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kr.hope.learn.coroutine.util.CoroutineCounter
import kr.hope.learn.coroutine.util.TestUtil.generate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BasicBuilderTest {
    /**
     * runTest 는 delay 함수에 의한 실행시간 지연을 skip 하기 때문에 runBlocking 으로 테스트 합니다.
     * @see https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/run-test.html
     */
    @Test
    fun launchTest() = runBlocking {
        // given
        val coroutineCount = 10
        val delayMillis = 100L
        val counter = CoroutineCounter()

        // when
        val startAt = System.currentTimeMillis()
        val jobs: List<Job> = generate(coroutineCount) {
            launch {
                delay(delayMillis)
                counter.increase()
            }
        }
        jobs.joinAll()
        val endAt = System.currentTimeMillis()

        // then
        val spentMillis = endAt - startAt
        assertThat(spentMillis).isLessThan(delayMillis * coroutineCount)
        assertThat(counter.get()).isEqualTo(coroutineCount)
        return@runBlocking
    }

    @Test
    fun asyncTest() = runBlocking {
        // given
        val coroutineCount = 10000
        val delayMillis = 100L
        val counter = CoroutineCounter()

        // when
        val startAt = System.currentTimeMillis()
        val deferred: List<Deferred<Unit>> = generate(coroutineCount) {
            async {
                delay(delayMillis)
                counter.increase()
            }
        }
        deferred.awaitAll()
        val endAt = System.currentTimeMillis()

        // then
        val spentMillis = endAt - startAt
        assertThat(spentMillis).isLessThan(delayMillis * coroutineCount)
        assertThat(counter.get()).isEqualTo(coroutineCount)
        return@runBlocking
    }
}