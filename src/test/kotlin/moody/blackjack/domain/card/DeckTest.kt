package moody.blackjack.domain.card

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class DeckTest : AnnotationSpec() {

    @Test
    fun `덱에서 카드를 한장 드로우 한다`() {
        // given
        val 스페이드2 = Card.of("스페이드", "2")
        val deck = Deck { Cards.from(mutableListOf(스페이드2)) }

        // when
        val result = deck.giveCard()

        // then
        result shouldBe 스페이드2
    }

    @Test
    fun `덱에서 카드를 드로우 할 때 카드가 없으면 예외가 발생한다`() {
        // given
        val deck = Deck { Cards.from(mutableListOf()) }

        // expect
        shouldThrow<IllegalArgumentException> {
            // when
            deck.giveCard()
        }.message shouldBe "카드뭉치에 카드가 없습니다."
    }

    @Test
    fun `게임 첫 턴 덱에서 카드를 두 장 드로우 한다`() {
        // given
        val 스페이드2 = Card.of("스페이드", "2")
        val 스페이드10 = Card.of("스페이드", "10")
        val 클로버5 = Card.of("클로버", "5")
        val deck = Deck { Cards.from(mutableListOf(스페이드2, 스페이드10, 클로버5)) }

        // when
        val result = deck.giveTwoCards()

        // then
        result shouldHaveSize 2
    }
}
