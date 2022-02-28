package moody.blackjack.domain.user

import moody.blackjack.domain.card.Deck
import moody.blackjack.domain.user.state.Blackjack
import moody.blackjack.domain.user.state.Bust
import moody.blackjack.domain.user.state.FirstTurn
import moody.blackjack.domain.user.state.Running
import moody.blackjack.domain.user.state.UserState

abstract class User(val name: String) {

    lateinit var state: UserState

    abstract fun isPlayer(): Boolean

    abstract fun isDealer(): Boolean

    fun drawInitialCards(deck: Deck) {
        state = FirstTurn.generateState(deck)
    }

    fun hit(deck: Deck) {
        changeState(state.draw(deck))
    }

    fun stay() {
        changeState(state.stay())
    }

    private fun changeState(state: UserState) {
        this.state = state
    }

    fun isRunning() = state is Running

    fun isBust() = state is Bust

    fun isBlackjack() = state is Blackjack

    fun getCards() = state.getCards()

    fun getScore() = getCards().calculateScore()

    fun isLessThan(user: User) = getScore().isLessThen(user.getScore())

    fun isBiggerThan(user: User) = getScore().isBiggerThen(user.getScore())
}
