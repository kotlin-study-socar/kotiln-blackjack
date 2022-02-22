package moody.blackjack.domain.card

class Card private constructor(suit: String, denomination: String) {
    val suit: Suit
    val denomination: Denomination

    init {
        this.suit = Suit.from(suit)
        this.denomination = Denomination.from(denomination)
    }

    companion object {
        fun of(suit: String, denomination: String): Card {
            return Card(suit, denomination)
        }
    }
}
