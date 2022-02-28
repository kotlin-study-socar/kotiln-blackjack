package moody.blackjack.domain.user

import moody.blackjack.domain.card.Deck

class Users(private val users: List<User>) : List<User> by users {

    fun initializeCards(deck: Deck) = forEach { it.drawInitialCards(deck) }

    fun isThereRunningUser() = any { it.isRunning() }

    fun currentPlayer(): User? {
        return filter { it.isPlayer() }.find { it.isRunning() }
    }

    fun getPlayers(): List<Player> = filter { it.isPlayer() } as List<Player>

    fun getDealer() = find { it.isDealer() } as Dealer
}
