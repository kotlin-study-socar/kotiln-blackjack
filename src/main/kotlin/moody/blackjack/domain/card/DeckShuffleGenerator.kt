package moody.blackjack.domain.card

class DeckShuffleGenerator : DeckGenerator {

    override fun generate(): Cards {
        val cards = mutableListOf<Card>()
        for (suit in Suit.values()) {
            Denomination.values().mapTo(cards) { Card.of(suit.suit, it.denomination) }
        }
        cards.shuffle()
        return Cards.from(cards)
    }
}
