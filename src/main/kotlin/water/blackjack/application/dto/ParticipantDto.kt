package water.blackjack.application.dto

import water.blackjack.model.Card
import water.blackjack.model.Participant

data class ParticipantDto(
    val name: String,
    val cards: Set<Card>,
    val sumValue: Int = 0
){
    companion object {
        fun convertNameAndCards(participant: Participant): ParticipantDto {
            return ParticipantDto(participant.name, participant.showCards())
        }
    }
}
