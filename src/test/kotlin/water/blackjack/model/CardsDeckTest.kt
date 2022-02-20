package water.blackjack.model

import water.blackjack.exception.OutOfCardException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CardsDeckTest {
    private val totalCardCount= 52

    @Test
    fun `52장의 카드를 생성한다`() {
        val deck = CardsDeck()
        deck.offerCards(totalCardCount)

        assertThrows<OutOfCardException> {
            deck.offerCards(1)
        }
    }

    @Test
    fun `요청한 카드 수 만큼 서로 다른 카드를 반환한다`() {
        val deck = CardsDeck()
        val requiredCount = 2
        val offeredCards = deck.offerCards(requiredCount)
        assertEquals(offeredCards.size, requiredCount)
    }

    @Test
    fun `카드를 요청한 만큼 제공하면 전체 카드 수에서 이를 제외한 숫자의 카드만 남는다`() {
        val deck = CardsDeck()
        val requiredCount = 2
        deck.offerCards(requiredCount)

        deck.offerCards(totalCardCount-requiredCount)
        assertThrows<OutOfCardException> {
            deck.offerCards(1)
        }
    }
}
