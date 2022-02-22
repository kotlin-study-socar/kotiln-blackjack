package water.blackjack.ui

import water.blackjack.application.BlackJackService
import water.blackjack.application.dto.ParticipantDto
import water.blackjack.view.InputView
import water.blackjack.view.OutputView

class BlackJackController {
    private val playerNames = InputView.getPlayerNames()
    private val jackService = BlackJackService(playerNames)

    fun startGame() {
        OutputView.printParticipantsNameAndCards(jackService.startGame())
        requestPlayersForHitAndStay(jackService.getPlayersCanHit())
        repeat(jackService.countDealerCardUpdated()){
            OutputView.printDealerCardAdded()
        }
        OutputView.printTotalParticipantCardsAndSum(jackService.getParticipants())
    }

    private fun requestPlayersForHitAndStay(players: List<ParticipantDto>) {
        players.forEach { requestPlayerForHitAndStay(it.name) }
    }

    private fun requestPlayerForHitAndStay(playerName: String) {
        while (jackService.checkPlayerCanGetCard(playerName) && InputView.requestPlayerForOneMoreCard(playerName)) {
            OutputView.printParticipantCards(jackService.getOneCard(playerName))
        }
        jackService.updatePlayerToStay(playerName)
    }
}
