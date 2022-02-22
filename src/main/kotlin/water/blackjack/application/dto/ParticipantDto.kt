package water.blackjack.application.dto

import water.blackjack.model.Card
import water.blackjack.model.Participant
import water.blackjack.model.enums.GameStatus

data class ParticipantDto(
    val name: String,
    val cards: Set<Card>,
    val sumValue: Int = 0
){
    companion object {
        // 참가자가 HIT 상태라면 점수 총 합을 계산하지 않고, STAY 상태라면 총 합을 계산한 값(sumValue)을 반환한다
        fun convertToParticipantsInfoWithOptionalSum(participant: Participant) : ParticipantDto{
            if (participant.getStatus() == GameStatus.HIT){
                return ParticipantDto(participant.name, participant.showCards())
            }
            return ParticipantDto(participant.name, participant.showCards(),participant.getSumOfValues())
        }
    }
}
