package moody.blackjack.domain.card

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class ScoreTest : AnnotationSpec() {

    @Test
    fun `Score끼리 plus를 하면 점수 합의 value를 가진 Score 객체가 반한된다`() {
        //given
        val ten = Score.from(10)
        val seven = Score.from(7)

        //when
        val result = ten.plus(seven)

        //then
        result shouldBe Score.from(17)
    }

    @Test
    fun `점수가 21인지 체크한다`() {
        //given
        val twentyOne = Score.from(21)

        //when
        val result = twentyOne.isTwentyOne()

        //then
        result.shouldBeTrue()
    }

    @Test
    fun `점수가 21을 초과했을 경우 bust인지 체크한다`() {
        //given
        val bustedScore = Score.from(22)

        //when
        val result = bustedScore.isBust()

        //then
        result.shouldBeTrue()
    }

    @Test
    fun `Score 객체를 다른 Score와 비교해서 큰지 판단한다 - 메서드 선언하는 객체 기준`() {
        //given
        val ten = Score.from(10)
        val six = Score.from(6)

        //when
        val result = ten.isBiggerThen(six)

        //then
        result.shouldBeTrue()
    }

    @Test
    fun `Score 객체를 다른 Score와 비교해서 작은 지 판단한다 - 메서드 선언하는 객체 기준`() {
        //given
        val ten = Score.from(10)
        val six = Score.from(6)

        //when
        val result = six.isLessThen(ten)

        //then
        result.shouldBeTrue()
    }
}
