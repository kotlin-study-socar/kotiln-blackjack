package water.blackjack.model

import water.blackjack.model.enums.CardSuit
import water.blackjack.model.enums.CardValue

class ShuffleDeck : Deck() {
    private val cards = getRandomOneDeck()

    private fun getRandomOneDeck(): MutableSet<Card> {
        return CardSuit.values()
            .flatMap { suit ->
                CardValue.values().map { value -> Card(suit, value) }
            }
            .shuffled().toMutableSet()
    }

    override fun offerCards(count: Int): Set<Card> {
        if (count > cards.size) {
            addOneMoreDeck()
        }

        val chosenCards = mutableSetOf<Card>()
        repeat(count) {
            val randomCard = cards.iterator().next()
            chosenCards.add(randomCard)
            cards.remove(randomCard)
        }
        return chosenCards.toSet()
    }

    private fun addOneMoreDeck() {
        cards.addAll(getRandomOneDeck())
    }
}
