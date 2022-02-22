package moody.blackjack.domain

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

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
}
