package moody.blackjack.domain.card

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

internal class CardTest : AnnotationSpec() {

    @Test
    fun `문앵(Suit)와 숫자(value)를 가지는 카드를 생성한다`() {
        //given
        val suit = "스페이드"
        val denomination = "7"

        //when
        val result = Card.of(suit, denomination)

        //then
        result.suit shouldBe Suit.SPADE
        result.denomination shouldBe Denomination.SEVEN
    }
}
