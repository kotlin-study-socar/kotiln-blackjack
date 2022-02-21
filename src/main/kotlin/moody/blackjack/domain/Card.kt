package moody.blackjack.domain

class Card(type: String, denomination: String) {
    val suit: Suit
    val denomination: Denomination

    init {
        this.suit = Suit.valueOf(type)
        this.denomination = Denomination.valueOf(denomination)
    }

    companion object {
        fun of(type: String, denomination: String): Card {
            return Card(type, denomination)
        }
    }
}
