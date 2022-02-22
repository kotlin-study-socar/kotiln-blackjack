package water.blackjack.application.dto

import water.blackjack.model.Participant

data class ParticipantsDto(val participants: List<ParticipantDto>) {
    companion object {
        fun convertToParticipantsInfoWithOptionalSum(participants: List<Participant>): List<ParticipantDto> {
            return participants.map { ParticipantDto.convertToParticipantsInfoWithOptionalSum(it) }
        }
    }
}
