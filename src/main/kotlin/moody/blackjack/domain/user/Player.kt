package moody.blackjack.domain.user

import moody.blackjack.domain.game.WinOrLose
import moody.blackjack.domain.game.WinOrLose.DRAW
import moody.blackjack.domain.game.WinOrLose.LOSE
import moody.blackjack.domain.game.WinOrLose.WIN

class Player(name: String) : User(name) {

    override fun isPlayer() = true

    override fun isDealer() = false

    fun compareWithDealer(dealer: Dealer): WinOrLose {
        return when {
            isBust() -> LOSE
            isBlackjack() -> WIN
            dealer.isBust() -> WIN
            isBiggerThan(dealer) -> WIN
            isLessThan(dealer) -> LOSE
            else -> DRAW
        }
    }
}
