package water.blackjack.model

import water.blackjack.model.enums.GameStatus

abstract class Participant {
    abstract val name: String
    protected var gameStatus = GameStatus.HIT
    protected val cards = mutableSetOf<Card>()

    abstract fun isHit(): Boolean

    open fun showCards(): Collection<Card> {
        return cards
    }

    open fun startGame(deck: Deck) {
        cards.addAll(deck.offerCards(START_CARD_RECEIVE_COUNT))
    }

    fun updateToStay() {
        gameStatus = gameStatus.updateStatus()
    }

    fun getSumOfValues(): Int {
        val sumOfMainValues = cards.sumOf { it.rank.getValue() }
        var sumWithOptionValues = sumOfMainValues
        val aceCards = cards.filter { it.rank.isAce() }
        aceCards.forEach {
            if ((sumWithOptionValues + it.rank.getOptionValue()) <= Participant.CARD_SUM_LIMIT) {
                sumWithOptionValues += it.rank.getOptionValue()
            }
            return@forEach
        }
        return maxOf(sumOfMainValues, sumWithOptionValues)
    }

    fun offeredOneCard(deck: Deck) {
        cards.addAll(deck.offerCards(ONE_MORE_CARD_COUNT))
    }

    fun isBust() = getSumOfValues() > CARD_SUM_LIMIT

    fun isBlackJack() = getSumOfValues() == CARD_SUM_LIMIT && (cards.size == START_CARD_RECEIVE_COUNT)

    companion object {
        const val START_CARD_RECEIVE_COUNT = 2
        const val ONE_MORE_CARD_COUNT = 1
        const val CARD_SUM_LIMIT = 21
    }
}
