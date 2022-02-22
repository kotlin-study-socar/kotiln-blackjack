package moody.blackjack.domain.card

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class SuitTest : AnnotationSpec() {

    @Test
    fun `suit를 생성한다`() {
        //given
        val suit = "다이아몬드"

        //when
        val result = Suit.from(suit)

        //then
        result.name shouldBe "DIAMOND"
        result.suit shouldBe suit
    }

    @Test
    fun `존재하지 않는 suit를 생성하려고 하면 예외를 발생시킨다`() {
        //given
        val wrongSuit = "무디"

        //when
        val result = shouldThrowAny { Suit.from(wrongSuit) }

        //then
        result is IllegalArgumentException
        result.message shouldBe "무디는 유효하지 않은 형태입니다."
    }
}
