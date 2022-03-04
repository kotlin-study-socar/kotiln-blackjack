import ornn.controller.GameController

fun main() {
    val gameController = GameController()
    gameController.distributeTwoCards()
    gameController.printAllPlayers()
    gameController.askToTakeMoreCard()
    gameController.printAllPlayersWithScore()
    gameController.getResult()
}
