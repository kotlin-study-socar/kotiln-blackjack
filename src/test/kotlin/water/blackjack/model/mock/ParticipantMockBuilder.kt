package water.blackjack.model.mock

import water.blackjack.model.Participant

class ParticipantMockBuilder(override val name: String = "TEST") : Participant() {
    override fun isHit(): Boolean {
        return true
    }
}
