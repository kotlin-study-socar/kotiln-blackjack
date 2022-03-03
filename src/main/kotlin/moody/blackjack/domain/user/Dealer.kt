package moody.blackjack.domain.user

import moody.blackjack.domain.card.Card
import moody.blackjack.domain.card.Score

class Dealer : User(DEALER_NAME) {

    override fun isPlayer() = false

    override fun isDealer() = true

    fun showOneCard(): Card {
        return getCards().first()
    }

    fun isAbleToDraw(): Boolean {
        return getCards().isLessThen(MIN_SCORE_TO_STAND)
    }

    companion object {
        private const val DEALER_NAME = "딜러"
        private val MIN_SCORE_TO_STAND = Score.from(17)
    }
}
