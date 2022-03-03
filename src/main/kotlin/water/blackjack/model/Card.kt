package water.blackjack.model

import water.blackjack.model.enums.CardSuit
import water.blackjack.model.enums.CardValue

class Card(
    val suit: CardSuit,
    val rank: CardValue
) {
    fun getValue() = rank.mainValue
    fun getWithOptionValue() = rank.mainValue + rank.optionValue

    override fun equals(other: Any?): Boolean {
        return (other is Card) && ((other.rank == this.rank) && (other.suit == this.suit))
    }

    override fun hashCode() = 31 * suit.hashCode() + rank.hashCode()
}
