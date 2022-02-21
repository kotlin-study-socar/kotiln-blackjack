package water.blackjack.view

import water.blackjack.application.dto.ParticipantDto

object OutputView {
    fun printParticipantsNameAndCards(participants: List<ParticipantDto>){
        for (participant in participants) {
            println("${participant.name}: ${participant.cards.joinToString(",") { it.showName }}")
        }
        println()
    }
}
