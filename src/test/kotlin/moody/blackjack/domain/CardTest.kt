package moody.blackjack.domain

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

internal class CardTest : AnnotationSpec() {

    @Test
    fun `문앵(Suit)와 숫자(value)를 가지는 카드를 생성한다`() {
        //given
        val suit = Suit.SPADE
        val denomination = Denomination.SEVEN

        //when
        val result = Card.of(suit.name, denomination.name)

        //then
        result.suit shouldBe suit
        result.denomination shouldBe denomination
    }
}
