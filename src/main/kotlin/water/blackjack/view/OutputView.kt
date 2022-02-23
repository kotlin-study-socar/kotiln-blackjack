package water.blackjack.view

import water.blackjack.application.dto.GameResultStringDto
import water.blackjack.application.dto.ParticipantDto

object OutputView {
    private const val DEALER_MESSAGE = "딜러는 16이하라 한장의 카드를 더 받았습니다.\n"

    fun showParticipantsCardsWhenBegin(participants: List<ParticipantDto>){
        participants.forEach { println("${it.name}: ${it.cards.joinToString(",") { it.showName }}") }.also { println() }
    }

    fun showPlayerCards(participant: ParticipantDto) {
        println("${participant.name} 카드: ${participant.cards.joinToString(",") { it.showName }}")
    }

    fun showDealerCardIsUpdated() {
        println(DEALER_MESSAGE)
    }

    fun showTotalParticipantCardsWithSum(participants: List<ParticipantDto>) {
        participants.forEach { println("${it.name}: ${it.cards.joinToString(",") { it.showName }}" +
                "- 결과: ${it.sumValue}") }.also { println() }
    }

    fun showGameResults(totalWinAndLoseResult: List<GameResultStringDto>) {
        totalWinAndLoseResult.forEach{ println(it.resultMessage) }
    }
}
