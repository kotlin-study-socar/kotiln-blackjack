package ornn.service

import ornn.domain.Cards
import ornn.domain.User
import ornn.domain.Users
import ornn.dto.PlayerDto
import ornn.exception.InputIllegalException
import ornn.resource.MaxNumber
import ornn.ui.InputView
import ornn.ui.OutputView

class UserService(private val users: Users) {
    companion object {
        const val SAY_YES = "y"
        const val SAY_NO = "n"
    }

    private fun getOneCard(deck: Cards) {
        users.takeOneCard(deck)
    }

    fun getTwoCards(deck: Cards) {
        repeat(2) { getOneCard(deck) }
    }

    fun askUsersToTakeMoreCard(deck: Cards) {
        users.forEach { askUserToTakeMoreCard(it, deck) }
    }

    private fun askUserToTakeMoreCard(user: User, deck: Cards) {
        if (user.isBiggerScoreThan(MaxNumber.SCORE_MAX)) {
            OutputView.printCardsSumIsMoreThanScoreMax()
            return
        }
        val answer = InputView.askUserToTakeMoreCard(user.getName())
        try {
            InputIllegalException.validateInputIsYesOrNo(answer)
        } catch (e: InputIllegalException) {
            println(e.message)
            askUserToTakeMoreCard(user, deck)
        }
        giveCardAccordingToTheAnswer(answer, user, deck)
    }

    private fun giveCardAccordingToTheAnswer(answer: String, user: User, deck: Cards) {
        when (answer.lowercase()) {
            SAY_YES -> {
                user.takeCard(deck)
                OutputView.printlnPlayer(PlayerDto.fromPlayer(user))
                askUserToTakeMoreCard(user, deck)
            }
            SAY_NO -> {
                OutputView.printlnPlayer(PlayerDto.fromPlayer(user))
            }
        }
    }
}
