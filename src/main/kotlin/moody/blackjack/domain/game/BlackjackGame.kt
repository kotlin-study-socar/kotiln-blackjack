package moody.blackjack.domain.game

import moody.blackjack.domain.card.Deck
import moody.blackjack.domain.user.User
import moody.blackjack.domain.user.Users

class BlackjackGame(
    private val deck: Deck,
    val users: Users
) {

    fun initUserCards() {
        users.initializeCards(deck)
    }

    fun hasRemainingUsers() = users.isThereRunningUser()

    fun giveCardToPlayer(answer: String) {
        val currentPlayer = currentPlayer()
        when (answer) {
            CALL -> currentPlayer.hit(deck)
            STAY -> currentPlayer.stay()
        }
    }

    fun currentPlayer(): User {
        return users.currentPlayer() ?: throw IllegalArgumentException("남은 플레이어가 없습니다.")
    }

    fun isDealerAbleToDraw() = users.getDealer().isAbleToDraw()

    fun giveCardToDealer() {
        users.getDealer().hit(deck)
    }

    fun calculateResult(): GameResult {
        val result = GameResult()
        result.addPlayersResult(users.getDealer(), users.getPlayers())
        return result
    }

    companion object {
        private const val CALL = "y"
        private const val STAY = "n"
    }
}
