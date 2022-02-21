package water.blackjack.model

import water.blackjack.exception.AlreadyStayStatusException
import water.blackjack.exception.ExceptionMessages
import water.blackjack.model.enums.GameStatus

abstract class Participant {
    abstract val name: String
    protected var gameStatus = GameStatus.HIT
    protected val cards = mutableSetOf<Card>()
    protected open val gameStopSumBoundary = CARD_SUM_LIMIT

    open fun showCards(): Set<Card> {
        return cards.toSet()
    }

    fun startAndReceiveTwoCards(deck: CardsDeck) {
        cards.addAll(deck.offerCards(START_CARD_RECEIVE_COUNT))
    }

    fun updateToStayStatus() {
        if (gameStatus == GameStatus.HIT){
            gameStatus = GameStatus.STAY
            return
        }
        throw AlreadyStayStatusException(ExceptionMessages.ALREADY_STAY_GAME_EXCEPTION)
    }

    companion object {
        const val START_CARD_RECEIVE_COUNT = 2
        const val CARD_SUM_LIMIT = 21
    }
}
