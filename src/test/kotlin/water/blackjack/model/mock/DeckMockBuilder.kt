package water.blackjack.model.mock

import water.blackjack.model.Card
import water.blackjack.model.Deck
import water.blackjack.model.enums.CardSuit
import water.blackjack.model.enums.CardValue

object DeckMockBuilder {
    class DeckMock(var cards: MutableSet<Card>) : Deck() {
        override fun offerCards(count: Int): Set<Card> {
            val chosenCards = mutableSetOf<Card>()
            if (count > cards.size) {
                cards.addAll(getRandomCards())
            }
            repeat(count) {
                val random = cards.iterator().next()
                chosenCards.add(random)
                cards.remove(random)
            }
            return chosenCards.toSet()
        }
        fun getRemainingCardsSize() = cards.size
    }

    fun buildCardsWithParam(cards: Set<Card>): Deck {
        return DeckMock(cards.toMutableSet())
    }

    fun buildShuffledCards(): Deck {
        return DeckMock(getRandomCards())
    }

    private fun getRandomCards(): MutableSet<Card> {
        return CardSuit.values()
            .flatMap { suit ->
                CardValue.values().map { value -> Card(suit, value) }
            }
            .shuffled().toMutableSet()
    }
}
