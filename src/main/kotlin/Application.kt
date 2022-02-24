import ornn.domain.Game
import ornn.domain.Users
import ornn.service.GameService
import ornn.service.InputService
import ornn.service.OutputService

fun main() {
    OutputService.askName()
    val usersName = InputService.getUsersName()
    val users = Users.fromNames(usersName)
    val game = Game(users)
    val gameService = GameService(game)

    gameService.distributeTwoCards()
    OutputService.printDistriButeTwoCards(gameService.findDealer(), gameService.findUsersName())
    OutputService.printAllPlayers(gameService.findUsers(), gameService.findDealer())

    gameService.askToUsersTakeMoreCard()
    gameService.askDealerToTakeMoreCard()
    OutputService.printAllPlayersWithScore(gameService.findUsers(), gameService.findDealer())

    gameService.updateResult()
    OutputService.printAllPlayersResult(game)
}
