package moody.blackjack.domain.user.state

import moody.blackjack.domain.card.Cards

abstract class Started(private val cards: Cards) : UserState {
    override fun getCards() = cards
}
