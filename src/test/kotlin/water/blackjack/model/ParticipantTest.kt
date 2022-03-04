package water.blackjack.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import water.blackjack.exception.BlackjackException
import water.blackjack.exception.ExceptionMessages
import water.blackjack.model.enums.CardSuit
import water.blackjack.model.enums.CardValue
import water.blackjack.model.mock.DeckMockBuilder
import water.blackjack.model.mock.ParticipantMockBuilder

class ParticipantTest {
    private val cardsDeck = ShuffleDeck()
    lateinit var participant: ParticipantMockBuilder

    @BeforeEach
    fun init() {
        participant = ParticipantMockBuilder()
    }

    @Test
    fun `참가자는 게임 시작 시 2장을 받는다`() {
        // when
        participant.startGame(cardsDeck)
        // then
        assertEquals(2, participant.showCards().size)
    }

    @Test
    fun `참가자는 hit(기본 값) 에서 stay 로 상태를 한 번 변경할 수 있으며 stay 상태에서 stay로의 변경을 요청한다면 예외가 발생한다`() {
        // given
        participant.updateToStay()
        // expect
        val exception = assertThrows<BlackjackException> {
            // when
            participant.updateToStay()
        }
        // then
        assertEquals(ExceptionMessages.ALREADY_STAY_STATE_EXCEPTION, exception.message)
    }
    @Test
    fun `ACE 카드가 포함되어있을 때 다른 카드 한 장과의 합이 21을 넘지 않으면 ACE를 11로 계산한다`() {
        // given: 'Ace' 카드와 'King' 카드를 카드 셋에 추가
        val kingCardValue = 10
        val aceValue = 11
        val deckMock = DeckMockBuilder.buildCardsWithParam(cardsWithAceCardUnderSumLimit)
        // when
        participant.startGame(deckMock)
        // then
        assertEquals(aceValue + kingCardValue, participant.getSumOfValues())
    }

    @Test
    fun `ACE 카드가 포함되어있을 때 다른 카드와의 합이 21을 넘는다면 ACE를 1로 계산한다`() {
        // given: Ace 카드, King 카드, 9 카드를 카드 셋에 추가
        val kingCardValue = 10
        val cardNineValue = 9
        val aceValue = 1
        val deckMock = DeckMockBuilder.buildCardsWithParam(cardsWithAceCardOverSumLimit)
        // when
        participant.startGame(deckMock)
        participant.offeredOneCard(deckMock)
        // then
        assertEquals(aceValue + kingCardValue + cardNineValue, participant.getSumOfValues())
    }

    @Test
    fun `Ace 카드 두 장과 9라는 카드가 있으면 Ace 카드 1장은 1점, 다른 한장은 11점으로 계산하여 21(1+11+9)을 반환한다`() {
        // given: 두 장의 Ace 카드와 9 카드를 카드 셋에 추가
        val deckMock = DeckMockBuilder.buildCardsWithParam(twoAceCardsWithCardNine)
        // when
        participant.startGame(deckMock)
        participant.offeredOneCard(deckMock)
        // then
        assertEquals(21, participant.getSumOfValues())
    }

    @Test
    fun `참가자가 받은 카드의 합이 21점이 넘었다면 버스트(Bust) 된다`() {
        // given
        val deckMock = DeckMockBuilder.buildCardsWithParam(bustCards)
        // when
        participant.startGame(deckMock)
        participant.offeredOneCard(deckMock)
        // then
        assertEquals(true, participant.isBust())
        assertEquals(false, participant.isBlackJack())
    }

    @Test
    fun `참가자가 받은 카드가 모두 두 장이며 두 장의 합이 21이면 블랙잭이 된다`() {
        // given
        val deckMock = DeckMockBuilder.buildCardsWithParam(blackJackCards)
        // when
        participant.startGame(deckMock)
        // then
        assertEquals(false, participant.isBust())
        assertEquals(true, participant.isBlackJack())
    }

    @Test
    fun `참가자가 받은 카드가 두 장을 초과하며 카드의 합이 21이면 블랙잭이 아니다`() {
        // given
        val deckMock = DeckMockBuilder.buildCardsWithParam(twoAceCardsWithCardNine)
        // when
        participant.startGame(deckMock)
        participant.offeredOneCard(deckMock)
        // then
        assertEquals(21, participant.getSumOfValues())
        assertEquals(3, participant.showCards().size)
        assertEquals(false, participant.isBlackJack())
    }

    companion object {
        val cardsWithAceCardUnderSumLimit = setOf(
            Card(CardSuit.SPADE, CardValue.ACE),
            Card(CardSuit.SPADE, CardValue.KING)
        )

        val cardsWithAceCardOverSumLimit = setOf(
            Card(CardSuit.SPADE, CardValue.ACE),
            Card(CardSuit.SPADE, CardValue.KING),
            Card(CardSuit.SPADE, CardValue.NINE)
        )
        val twoAceCardsWithCardNine = setOf(
            Card(CardSuit.SPADE, CardValue.ACE),
            Card(CardSuit.HEART, CardValue.ACE),
            Card(CardSuit.SPADE, CardValue.NINE)
        )

        val bustCards = setOf(
            Card(CardSuit.HEART, CardValue.KING),
            Card(CardSuit.HEART, CardValue.QUEEN),
            Card(CardSuit.HEART, CardValue.FIVE)
        )

        val blackJackCards = setOf(
            Card(CardSuit.HEART, CardValue.ACE),
            Card(CardSuit.HEART, CardValue.QUEEN)
        )
    }
}
