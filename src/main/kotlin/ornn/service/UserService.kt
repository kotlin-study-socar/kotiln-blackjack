package ornn.service

import ornn.domain.Cards
import ornn.domain.Users

class UserService(private val users: Users) {

    private fun getOneCard(gameCards: Cards) {
        users.takeOneCard(gameCards)
    }

    fun getTwoCards(gameCards: Cards) {
        repeat(2) { getOneCard(gameCards) }
    }

    fun askedToTakeMoreCard(opCards: Cards) {
        users.forEach {
            it.askedToTakeMoreCard(opCards)
        }
    }
}
