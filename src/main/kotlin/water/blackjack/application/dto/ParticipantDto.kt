package water.blackjack.application.dto

import water.blackjack.model.Card
import water.blackjack.model.Participant

data class ParticipantDto(
    val name: String,
    val cards: Collection<Card>,
    val sumValue: Int = 0
) {
    companion object {
        fun convertWithoutSumValue(participant: Participant): ParticipantDto {
            return ParticipantDto(participant.name, participant.showCards())
        }

        fun convertWithSumValue(participant: Participant): ParticipantDto {
            return ParticipantDto(participant.name, participant.showCards(), participant.getSumOfValues())
        }
    }
}
