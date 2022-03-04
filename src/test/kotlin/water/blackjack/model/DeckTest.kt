package water.blackjack.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import water.blackjack.model.mock.DeckMockBuilder
import water.blackjack.model.mock.DeckMockBuilder.DeckMock

class DeckTest {
    private val totalCardCount = 52

    @Test
    fun `52장의 카드를 생성한다`() {
        // given
        val deck = DeckMockBuilder.buildShuffledCards() as DeckMock
        // when
        deck.offerCards(totalCardCount)
        // then
        assertEquals(0, deck.getRemainingCardsSize())
    }

    @Test
    fun `52장의 카드를 다 사용했다면 매번 새로운 덱을 추가한다`() {
        // given
        val deck = DeckMockBuilder.buildShuffledCards() as DeckMock
        // when
        deck.offerCards(totalCardCount)
        deck.offerCards(1)
        // then
        assertEquals(totalCardCount - 1, deck.getRemainingCardsSize())
    }

    @Test
    fun `요청한 카드 수 만큼 서로 다른 카드를 반환한다`() {
        // given
        val deck = ShuffleDeck()
        val requiredCount = 2
        // when
        val offeredCards = deck.offerCards(requiredCount).toList()
        // then
        assertEquals(requiredCount, offeredCards.size)
        assertNotEquals(offeredCards[0], offeredCards[1])
    }

    @Test
    fun `카드를 요청한 만큼 제공하면 전체 카드 수에서 이를 제외한 숫자의 카드만 남는다`() {
        // given
        val deck = DeckMockBuilder.buildShuffledCards() as DeckMock
        val requiredCount = 2
        // when
        deck.offerCards(requiredCount)
        // then
        assertEquals(deck.getRemainingCardsSize(), totalCardCount - requiredCount)
    }
}
