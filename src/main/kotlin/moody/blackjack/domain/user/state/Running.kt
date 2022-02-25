package moody.blackjack.domain.user.state

import moody.blackjack.domain.card.Cards

abstract class Running(cards: Cards) : Started(cards) {

    override fun isFinished(): Boolean {
        return false
    }
}
