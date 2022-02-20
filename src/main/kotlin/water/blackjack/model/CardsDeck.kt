package water.blackjack.model

import water.blackjack.exception.ExceptionMessages
import water.blackjack.exception.OutOfCardException

class CardsDeck {
    private val cards = CardSuit.values().flatMap {
                                            suit -> CardValue.values().map { value -> Card(suit, value) }
                                        }.shuffled().toMutableSet()

    fun offerCards(count: Int): Set<Card> {
        if (count > cards.size){
            throw OutOfCardException(ExceptionMessages.OUT_OF_CARD_MESSAGE)
        }

        val chosenCards = mutableSetOf<Card>()
        repeat(count) {
            val randomCard = cards.random()
            chosenCards.add(randomCard)
            cards.remove(randomCard)
        }
        return chosenCards.toSet()
    }
}
