package water.blackjack.model

import water.blackjack.model.enums.CardSuit
import water.blackjack.model.enums.CardValue

class Card(
    private val suit: CardSuit,
    private val rank: CardValue
) {
    val showName: String = rank.showName + suit.patternName

    override fun equals(other: Any?): Boolean {
        if (other is Card){
            return (other.rank == this.rank && other.suit == this.suit)
        }
        return false
    }

    override fun hashCode() = 31 *  suit.hashCode() + rank.hashCode()

    fun getValue() = rank.mainValue
    fun getWithOptionValue() = rank.mainValue + rank.optionValue
}
