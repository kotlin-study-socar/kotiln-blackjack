package water.blackjack.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import water.blackjack.exception.BlackJackException
import water.blackjack.exception.ExceptionMessages
import water.blackjack.model.enums.CardSuit
import water.blackjack.model.enums.CardValue

class ParticipantTest {
    private val cardsDeck = CardsDeck()
    lateinit var participant: TestParticipant
    private val cardsDeckMock = Mockito.mock(CardsDeck::class.java)

    @BeforeEach
    fun init() {
        participant = TestParticipant()
    }

    @Test
    fun `참가자는 게임 시작 시 2장을 받는다`() {
        participant.startGame(cardsDeck)
        assertEquals(participant.showCards().size,2)
    }

    @Test
    fun `참가자는 hit(기본 값) 에서 stay 로 상태를 한 번 변경할 수 있으며 stay 상태에서 stay로의 변경을 요청한다면 예외가 발생한다`() {
        participant.updateToStay()
        val exception = assertThrows<BlackJackException> {
            participant.updateToStay()
        }
        assertEquals(exception.message,ExceptionMessages.ALREADY_STAY_STATE_EXCEPTION)
    }
    @Test
    fun `ACE 카드가 포함되어있을 때 다른 카드와의 합이 21을 넘지 않으면 11로 계산한 값을 반환한다`() {
        // 'Ace' 카드와 'King' 카드를 카드 셋에 추가
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(cardsWithAceCardUnderSumLimit)
        participant.startGame(cardsDeckMock)

        val kingCardValue = 10
        val aceValue = 11
        assertEquals(participant.getSumOfValues(),aceValue+kingCardValue)
    }

    @Test
    fun `ACE 카드가 포함되어있을 때 다른 카드와의 합이 21을 넘는다면 1로 계산한 값을 반환한다`() {
        // 'Ace' 카드, 'King' 카드, '9' 카드를 카드 셋에 추가
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(cardsWithAceCardOverSumLimit)
        participant.startGame(cardsDeckMock)
        val kingCardValue = 10
        val cardNineValue = 9
        val aceValue = 1

        assertEquals(participant.getSumOfValues(),aceValue+kingCardValue+cardNineValue)
    }

    @Test
    fun `Ace 카드 두 장과 9라는 카드가 있으면 Ace 카드 1장은 1점, 다른 한장은 11점으로 계산하여 21을 반환한다`() {
        // 두 장의 'Ace' 카드와 '9' 카드를 카드 셋에 추가
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(twoAceCardsWithCardNine)
        participant.startGame(cardsDeckMock)

        assertEquals(participant.getSumOfValues(),21)
    }

    @Test
    fun `받은 카드의 합이 21점이 넘었다면 버스트(Bust) 된다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(bustCards)
        participant.startGame(cardsDeckMock)

        assertEquals(participant.isBust(),true)
        assertEquals(participant.isBlackJack(),false)
    }

    @Test
    fun `받은 카드가 모두 두 장이며 두 장의 합이 21이면 블랙잭이 된다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(blackJackCards)
        participant.startGame(cardsDeckMock)

        assertEquals(participant.isBust(),false)
        assertEquals(participant.isBlackJack(),true)
    }

    @Test
    fun `받은 카드가 두 장을 초과하며 카드의 합이 21이면 블랙잭이 아니다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(twoAceCardsWithCardNine)
        participant.startGame(cardsDeckMock)

        assertEquals(participant.getSumOfValues(),21)
        assertEquals(participant.showCards().size, 3)
        assertEquals(participant.isBlackJack(),false)
    }

    class TestParticipant(override val name: String = "TEST") : Participant(){
        override fun isHit(): Boolean {
            return true
        }
    }

    companion object {
        val cardsWithAceCardUnderSumLimit = setOf(
                                    Card(CardSuit.SPADE, CardValue.ACE),
                                    Card(CardSuit.SPADE,CardValue.KING))

        val cardsWithAceCardOverSumLimit = setOf(
                                    Card(CardSuit.SPADE,CardValue.ACE),
                                    Card(CardSuit.SPADE,CardValue.KING),
                                    Card(CardSuit.SPADE,CardValue.NINE))
        val twoAceCardsWithCardNine = setOf(
                                    Card(CardSuit.SPADE,CardValue.ACE),
                                    Card(CardSuit.HEART,CardValue.ACE),
                                    Card(CardSuit.SPADE,CardValue.NINE))

        val bustCards = setOf(
                                    Card(CardSuit.HEART,CardValue.KING),
                                    Card(CardSuit.HEART,CardValue.QUEEN),
                                    Card(CardSuit.HEART,CardValue.FIVE))

        val blackJackCards = setOf(
                                    Card(CardSuit.HEART,CardValue.ACE),
                                    Card(CardSuit.HEART,CardValue.QUEEN))
    }
}
