package water.blackjack.view

import water.blackjack.application.dto.CardDto
import water.blackjack.application.dto.GameResultStringDto
import water.blackjack.application.dto.ParticipantDto

object OutputView {
    private const val DEALER_MESSAGE = "딜러는 16이하라 한장의 카드를 더 받았습니다.\n"

    fun showParticipantsCardsWhenBegin(participants: List<ParticipantDto>) {
        participants.forEach { println("${it.name}: ${it.cards.joinToString { card -> getCardName(card) }}") }.also { println() }
    }

    fun showPlayerCards(participant: ParticipantDto) {
        println("${participant.name} 카드: ${participant.cards.joinToString { getCardName(it) }}")
    }

    fun showDealerCardIsUpdated() {
        println(DEALER_MESSAGE)
    }

    fun showTotalParticipantCardsWithSum(participants: List<ParticipantDto>) {
        participants.forEach {
            println("${it.name}: ${it.cards.joinToString { card -> getCardName(card) }}- 결과: ${it.sumValue}")
        }.also { println() }
    }

    fun showGameResults(totalWinAndLoseResult: List<GameResultStringDto>) {
        totalWinAndLoseResult.forEach { println(it.resultMessage) }
    }

    private fun getCardName(card: CardDto) = card.rank.showName + card.suit.patternName
}
