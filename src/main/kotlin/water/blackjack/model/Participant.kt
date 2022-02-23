package water.blackjack.model

import water.blackjack.exception.BlackJackException
import water.blackjack.exception.ExceptionMessages
import water.blackjack.model.enums.CardValue
import water.blackjack.model.enums.GameStatus

abstract class Participant {
    abstract val name: String
    protected var gameStatus = GameStatus.HIT
    protected val cards = mutableSetOf<Card>()
    protected open val gameStopSumBoundary = CARD_SUM_LIMIT

    abstract fun isHit(): Boolean

    open fun showCards(): Collection<Card> {
        return cards
    }

    open fun startGame(deck: CardsDeck) {
        cards.addAll(deck.offerCards(START_CARD_RECEIVE_COUNT))
    }

    fun updateToStay() {
        if (gameStatus == GameStatus.HIT) {
            gameStatus = GameStatus.STAY
            return
        }
        throw BlackJackException(ExceptionMessages.ALREADY_STAY_STATE_EXCEPTION)
    }

    fun getSumOfValues(): Int {
        val sumOfMainValues = cards.sumOf { it.getValue() }
        var sumWithOptionValues = sumOfMainValues
        val aceCardCount =
            cards.count { it.getWithOptionValue() == (CardValue.ACE.mainValue + CardValue.ACE.optionValue) }

        repeat(aceCardCount) {
            if ((sumWithOptionValues + CardValue.ACE.optionValue) <= CARD_SUM_LIMIT) {
                sumWithOptionValues += CardValue.ACE.optionValue
            }
        }
        return maxOf(sumOfMainValues, sumWithOptionValues)
    }

    fun getStatus() = gameStatus

    fun isBust() = getSumOfValues() > CARD_SUM_LIMIT

    fun isBlackJack() = getSumOfValues() == CARD_SUM_LIMIT && (cards.size == START_CARD_RECEIVE_COUNT)

    companion object {
        const val START_CARD_RECEIVE_COUNT = 2
        const val ONE_MORE_CARD_COUNT = 1
        const val CARD_SUM_LIMIT = 21
    }
}
