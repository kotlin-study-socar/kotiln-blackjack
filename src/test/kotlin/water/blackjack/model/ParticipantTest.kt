package water.blackjack.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import water.blackjack.exception.AlreadyStayStatusException
import water.blackjack.model.enums.CardSuit
import water.blackjack.model.enums.CardValue

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
    fun `참가자는 hit(기본 값) 에서 stay 로 상태를 한 번 변경할 수 있으며 stay 상태에서 stay로의 변경을 요청한다면 예외가 발생한다`() {
        participant.updateToStayStatus()
        assertThrows<AlreadyStayStatusException> {
            participant.updateToStayStatus()
        }
    }
    @Test
    fun `ACE 카드가 포함되어있을 때 다른 카드와의 합이 21을 넘지 않으면 11로 계산한 값을 반환한다`() {
        // 'Ace' 카드와 'King' 카드를 카드 셋에 추가
        participant.addSampleCardsWithAceCardUnderSumLimit()
        val kingCardValue = 10
        val aceValue = 11
        assertEquals(participant.getSumOfValues(),aceValue+kingCardValue)
    }

    @Test
    fun `ACE 카드가 포함되어있을 때 다른 카드와의 합이 21을 넘는다면 1로 계산한 값을 반환한다`() {
        // 'Ace' 카드, 'King' 카드, '9' 카드를 카드 셋에 추가
        participant.addSampleCardsWithAceCardOverSumLimit()
        val kingCardValue = 10
        val cardNineValue = 9
        val aceValue = 1
        assertEquals(participant.getSumOfValues(),aceValue+kingCardValue+cardNineValue)
    }

    @Test
    fun `Ace 카드 두 장과 9라는 카드가 있으면 Ace 카드 1장은 1점, 다른 한장은 11점으로 계산하여 21을 반환한다`() {
        // 두 장의 'Ace' 카드와 '9' 카드를 카드 셋에 추가
        participant.addTwoAceCardsWithCardNine()
        assertEquals(participant.getSumOfValues(),21)
    }

    class TestParticipant(override val name: String = "TEST") : Participant(){
        fun addSampleCardsWithAceCardUnderSumLimit() {
            cards.addAll(setOf(
                Card(CardSuit.SPADE, CardValue.ACE),
                Card(CardSuit.SPADE,CardValue.KING)))
        }

        fun addSampleCardsWithAceCardOverSumLimit() {
            cards.addAll(setOf(
                Card(CardSuit.SPADE,CardValue.ACE),
                Card(CardSuit.SPADE,CardValue.KING),
                Card(CardSuit.SPADE,CardValue.NINE)))
        }

        fun addTwoAceCardsWithCardNine(){
            cards.addAll(setOf(
                Card(CardSuit.SPADE,CardValue.ACE),
                Card(CardSuit.HEART,CardValue.ACE),
                Card(CardSuit.SPADE,CardValue.NINE)))
        }

        override fun canGetCard(): Boolean {
            return true
        }
    }
}
