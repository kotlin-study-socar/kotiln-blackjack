package moody.blackjack.domain.card

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import moody.blackjack.domain.card.Card
import moody.blackjack.domain.card.Cards
import moody.blackjack.domain.card.Deck

class DeckTest : AnnotationSpec() {

    @Test
    fun `덱에서 카드를 한장 드로우 한다`() {
        //given
        val spade2 = Card.of("스페이드", "2")
        val deck = Deck { Cards.from(mutableListOf(spade2)) }

        //when
        val result = deck.drawCard()

        //then
        result shouldBe spade2
    }

    @Test
    fun `덱에서 카드를 드로우 할 때 카드가 없으면 예외가 발생한다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf()) }

        //when
        val result = shouldThrow<IllegalArgumentException> { deck.drawCard() }

        //then
        result.message shouldBe "카드뭉치에 카드가 없습니다."
    }
}
