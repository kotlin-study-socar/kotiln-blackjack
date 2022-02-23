package water.blackjack.application.dto

import water.blackjack.model.Participant

data class ParticipantsDto(val participants: List<ParticipantDto>) {
    companion object {
        fun convertParticipantsWithoutSumValue(participants: List<Participant>): List<ParticipantDto> {
            return participants.map { ParticipantDto.convertWithoutSumValue(it) }
        }
        fun convertParticipantsWithSumValue(participants: List<Participant>): List<ParticipantDto> {
            return participants.map { ParticipantDto.convertWithSumValue(it) }
        }
    }
}
