package moody.blackjack.domain

class Deck(deckGenerator: DeckGenerator) {
    private val cards: Cards

    init {
        cards = deckGenerator.generate()
    }

    fun drawCard(): Card {
        return cards.draw()
    }
}
