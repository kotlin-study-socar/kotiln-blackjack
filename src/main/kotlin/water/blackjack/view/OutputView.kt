package water.blackjack.view

import water.blackjack.application.dto.ParticipantDto

object OutputView {
    private const val DEALER_MESSAGE = "딜러는 16이하라 한장의 카드를 더 받았습니다.\n"

    fun printParticipantsNameAndCards(participants: List<ParticipantDto>){
        participants.forEach { println("${it.name}: ${it.cards.joinToString(",") { it.showName }}") }.also { println() }
    }

    fun printParticipantCards(participant: ParticipantDto) {
        println("${participant.name} 카드: ${participant.cards.joinToString(",") { it.showName }}")
    }

    fun printDealerCardAdded() {
        println(DEALER_MESSAGE)
    }

    fun printTotalParticipantCardsAndSum(participants: List<ParticipantDto>) {
        participants.forEach { println("${it.name}: ${it.cards.joinToString(",") { it.showName }}" +
                "- 결과: ${it.sumValue}") }.also { println() }
    }
}
