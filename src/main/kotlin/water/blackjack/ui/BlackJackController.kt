package water.blackjack.ui

import water.blackjack.application.BlackJackService
import water.blackjack.view.InputView
import water.blackjack.view.OutputView

class BlackJackController {
    private val playerNames = InputView.getPlayerNames()
    private val jackService = BlackJackService(playerNames)

    fun startGame() {
        OutputView.printParticipantsNameAndCards(jackService.startGame())
    }
}
