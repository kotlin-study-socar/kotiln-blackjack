package water.blackjack.ui

import water.blackjack.application.BlackjackService
import water.blackjack.application.dto.ParticipantDto
import water.blackjack.view.InputView
import water.blackjack.view.OutputView

class BlackjackController {
    private val playerNames = InputView.getPlayerNames()
    private val blackjackService = BlackjackService(playerNames)

    fun startGame() {
        OutputView.showParticipantsCardsWhenBegin(blackjackService.startGame())
        requestPlayersForHitAndStay(blackjackService.getHitPlayers())
        repeat(blackjackService.getCountsOfUpdatedDealerCards()) {
            OutputView.showDealerCardIsUpdated()
        }
        OutputView.showTotalParticipantCardsWithSum(blackjackService.getParticipantsWithSumValue())
        OutputView.showGameResults(blackjackService.getTotalWinAndLoseResults())
    }

    private fun requestPlayersForHitAndStay(players: List<ParticipantDto>) {
        players.forEach { requestPlayerForHitAndStay(it.name) }
    }

    private fun requestPlayerForHitAndStay(playerName: String) {
        while (blackjackService.isHitPlayer(playerName) && InputView.requestPlayerForOneMoreCard(playerName)) {
            OutputView.showPlayerCards(blackjackService.offerOneCard(playerName))
        }
        blackjackService.updatePlayerToStay(playerName)
    }
}
