package moody.blackjack.domain.card

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class DenominationTest : AnnotationSpec() {

    @Test
    fun `denomination을 생성한다`() {
        //given
        val denomination = "A"

        //when
        val result = Denomination.from(denomination)

        //then
        result.name shouldBe "ACE"
        result.denomination shouldBe denomination
    }

    @Test
    fun `존재하지 않는 denomination을 생성하려고 하면 예외를 발생시킨다`() {
        //given
        val wrongDenomination = "13"

        //when
        val result = shouldThrowAny { Denomination.from(wrongDenomination) }

        //then
        result is IllegalArgumentException
        result.message shouldBe "13는 유효하지 않은 숫자입니다."
    }

    @Test
    fun `denomination이 A라면 isAce는 true를 반환한다`() {
        //given
        val denomination = "A"
        val ace = Denomination.from(denomination)

        //when
        val result = ace.isAce()

        //then
        result shouldBe true
    }
}
