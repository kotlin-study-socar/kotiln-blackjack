package moody.blackjack.domain.user.state

import moody.blackjack.domain.card.Cards
import moody.blackjack.domain.card.Deck

interface UserState {

    fun draw(deck: Deck): UserState

    fun stay(): UserState

    fun isFinished(): Boolean

    fun getCards(): Cards
}
