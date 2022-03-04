package ornn.controller

import ornn.domain.Game
import ornn.domain.Users
import ornn.exception.InputIllegalException
import ornn.exception.InputIllegalException.Companion.validateNullInputException
import ornn.service.GameService
import ornn.ui.InputView
import ornn.ui.OutputView

class GameController {

    private val gameService: GameService

    init {
        val usersName = getUsersName()
        val users = Users.fromNames(usersName)
        val game = Game(users)
        gameService = GameService(game)
    }

    private fun getUsersName(): List<String> {
        return try {
            val usersName = InputView.getUsersName()
            validateNullInputException(usersName)
            usersName.split(",")
        } catch (e: InputIllegalException) {
            println(e.message)
            getUsersName()
        }
    }

    fun distributeTwoCards() {
        gameService.distributeTwoCards()
        OutputView.printDistriButingTwoCards(gameService.findDealer(), gameService.findUsersName())
    }

    fun printAllPlayers() {
        OutputView.printAllPlayers(gameService.findUsers(), gameService.findDealer())
    }

    fun askToTakeMoreCard() {
        gameService.askToUsersTakeMoreCard()
        gameService.askDealerToTakeMoreCard()
    }

    fun printAllPlayersWithScore() {
        OutputView.printAllPlayersWithScore(gameService.findUsers(), gameService.findDealer())
    }

    fun getResult() {
        gameService.updateResult()
        OutputView.printAllPlayersResult(gameService.findGame())
    }
}
