package moody.blackjack.domain

class DeckShuffleGenerator : DeckGenerator {

    override fun generate(): Cards {
        val cards = mutableListOf<Card>()
        for (suit in Suit.values()) {
            Denomination.values().mapTo(cards) { Card.of(suit.name, it.name) }
        }
        cards.shuffle()
        return Cards.from(cards)
    }
}
