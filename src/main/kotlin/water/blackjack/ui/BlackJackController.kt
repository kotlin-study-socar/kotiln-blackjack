package water.blackjack.ui

import water.blackjack.application.BlackJackService
import water.blackjack.application.dto.ParticipantDto
import water.blackjack.view.InputView
import water.blackjack.view.OutputView

class BlackJackController {
    private val playerNames = InputView.getPlayerNames()
    private val jackService = BlackJackService(playerNames)

    fun startGame() {
        OutputView.showParticipantsCardsWhenBegin(jackService.startGame())
        requestPlayersForHitAndStay(jackService.getHitPlayers())
        repeat(jackService.getCountsOfUpdatedDealerCards()){
            OutputView.showDealerCardIsUpdated()
        }
        OutputView.showTotalParticipantCardsWithSum(jackService.getParticipants())
        OutputView.showGameResults(jackService.getTotalWinAndLoseResults())
    }

    private fun requestPlayersForHitAndStay(players: List<ParticipantDto>) {
        players.forEach { requestPlayerForHitAndStay(it.name) }
    }

    private fun requestPlayerForHitAndStay(playerName: String) {
        while (jackService.isHitPlayer(playerName) && InputView.requestPlayerForOneMoreCard(playerName)) {
            OutputView.showPlayerCards(jackService.offerOneCard(playerName))
        }
        jackService.updatePlayerToStay(playerName)
    }
}
