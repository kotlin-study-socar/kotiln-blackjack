package ornn.service

import ornn.domain.Cards
import ornn.domain.Users

class UserService(private val users: Users) {

    private fun getOneCard(opCards: Cards) {
        users.takeOneCard(opCards)
    }

    fun getTwoCards(opCards: Cards) {
        repeat(2) { getOneCard(opCards) }
    }

    fun askedToTakeMoreCard(opCards: Cards) {
        users.forEach { it.askedToTakeMoreCard(opCards) }
    }
}
