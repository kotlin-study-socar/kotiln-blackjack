package moody.blackjack.domain.card

class Deck(deckGenerator: DeckGenerator) {
    private val cards: Cards

    init {
        cards = deckGenerator.generate()
    }

    fun giveCard(): Card {
        if (cards.size <= EMPTY_SIZE) {
            throw IllegalArgumentException("카드뭉치에 카드가 없습니다.")
        }
        return cards.removeFirst()
    }

    fun giveTwoCards(): Cards {
        val initialCards = mutableListOf<Card>()
        repeat(2) {
            initialCards.add(giveCard())
        }
        return Cards.from(initialCards)
    }

    companion object {
        private const val EMPTY_SIZE = 0
    }
}
