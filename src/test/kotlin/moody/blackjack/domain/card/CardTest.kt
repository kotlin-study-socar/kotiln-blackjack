package moody.blackjack.domain.card

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows

internal class CardTest : AnnotationSpec() {

    @Test
    fun `suit와 denomination을 가지는 카드를 생성한다`() {
        //given
        val suit = "스페이드"
        val denomination = "7"

        //when
        val result = Card.of(suit, denomination)

        //then
        result.suit shouldBe Suit.SPADE
        result.denomination shouldBe Denomination.SEVEN
    }

    @Test
    fun `Card의 suit가 유효하지 않은 경우 예외를 발생시킨다`() {
        //given
        val wrongSuit = "별"
        val denomination = "7"

        //expect
        assertThrows<IllegalArgumentException> {
            //when
            Card.of(wrongSuit, denomination)
        }
    }

    @Test
    fun `Card의 denomination이 유효하지 않은 경우 예외를 발생시킨다`() {
        //given
        val suit = "스페이드"
        val wrongDenomination = "77"

        //expect
        assertThrows<IllegalArgumentException> {
            //when
            Card.of(suit, wrongDenomination)
        }
    }
}
