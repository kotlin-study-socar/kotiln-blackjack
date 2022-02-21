package water.blackjack.application.dto

import water.blackjack.model.Participant

data class ParticipantsDto(val participants: List<ParticipantDto>) {
    companion object {
        fun convertNameAndCards(participants: List<Participant>): List<ParticipantDto> {
            return participants.map { ParticipantDto.convertNameAndCards(it) }
        }
    }
}
