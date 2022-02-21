package water.blackjack.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import water.blackjack.exception.AlreadyStayStatusException

class ParticipantTest {
    private val cardsDeck = CardsDeck()
    lateinit var participant: TestParticipant

    @BeforeEach
    fun init() {
        participant = TestParticipant()
    }

    @Test
    fun `참가자는 게임 시작 시 2장을 받는다`() {
        participant.startAndReceiveTwoCards(cardsDeck)
        assertEquals(participant.showCards().size,2)
    }

    @Test
    fun `참가자는 hit 에서 stay 로 상태를 한 번 변경할 수 있으며 stay 상태에서 stay로의 변경을 요청한다면 예외가 발생한다`() {
        participant.updateToStayStatus()
        assertThrows<AlreadyStayStatusException> {
            participant.updateToStayStatus()
        }
    }

    class TestParticipant(override val name: String = "TEST") : Participant()
}
