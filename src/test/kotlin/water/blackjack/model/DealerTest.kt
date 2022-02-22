package water.blackjack.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import water.blackjack.exception.BlackJackException
import water.blackjack.exception.ExceptionMessages
import water.blackjack.model.enums.CardSuit
import water.blackjack.model.enums.CardValue
import water.blackjack.model.enums.GameResult

class DealerTest {
    private val deck = CardsDeck()
    lateinit var dealer: Dealer
    lateinit var player: Player
    private val cardsDeckMock = mock(CardsDeck::class.java)

    @BeforeEach
    fun init() {
        dealer = Dealer()
        player = Player("player")
    }

    @Test
    fun `딜러가 hit 상태라면 오픈된 한장의 카드만 반환한다`() {
        dealer.startAndReceiveTwoCards(deck)
        assertEquals(dealer.showCards().size,1)
    }

    @Test
    fun `딜러가 stay 상태라면 모든 카드를 반환한다`() {
        dealer.startAndReceiveTwoCards(deck)
        dealer.updateToStayStatus()
        assertEquals(dealer.showCards().size,2)
    }

    @Test
    fun `딜러는 마지막에 추가로 뽑은 카트의 개수를 반환한다`() {
        val countOfAddedCards = dealer.getCountOfAddedCards(deck)
        assertEquals(countOfAddedCards,dealer.showCards().size)
    }

    @Test
    fun `딜러는 추가 카드를 뽑는 과정을 거치고 난 뒤 Stay 상태가 된다`() {
        dealer.getCountOfAddedCards(deck)
        val exception = assertThrows<BlackJackException> {
            dealer.updateToStayStatus()
        }
        assertEquals(exception.message, ExceptionMessages.ALREADY_STAY_STATE_EXCEPTION)
    }

    @Test
    fun `딜러와 플레이어가 버스트면 플레이어가 이긴다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(bustCards)
        player.startAndReceiveTwoCards(cardsDeckMock)
        dealer.startAndReceiveTwoCards(cardsDeckMock)

        assertEquals(player.isBust(),true)
        assertEquals(dealer.isBust(),true)
        assertEquals(dealer.getPlayerGameResult(player), GameResult.WIN)
    }

    @Test
    fun `플레이어만 블랙잭이면 플레이어가 이긴다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(blackJackCards)
        player.startAndReceiveTwoCards(cardsDeckMock)
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum20)
        dealer.startAndReceiveTwoCards(cardsDeckMock)

        assertEquals(player.isBlackJack(),true)
        assertEquals(dealer.isBlackJack(),false)
        assertEquals(dealer.getPlayerGameResult(player), GameResult.WIN)
    }

    @Test
    fun `플레이어가 버스트가 아니면서 딜러보다 점수가 높으면 플레이어가 이긴다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum20)
        player.startAndReceiveTwoCards(cardsDeckMock)
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum17)
        dealer.startAndReceiveTwoCards(cardsDeckMock)

        assertEquals(player.isBust(),false)
        assertTrue(player.getSumOfValues() > dealer.getSumOfValues())
        assertEquals(dealer.getPlayerGameResult(player), GameResult.WIN)
    }

    @Test
    fun `플레이어만 버스트라면 플레이어가 진다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(bustCards)
        player.startAndReceiveTwoCards(cardsDeckMock)
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum20)
        dealer.startAndReceiveTwoCards(cardsDeckMock)

        assertEquals(player.isBust(),true)
        assertEquals(dealer.isBust(),false)
        assertEquals(dealer.getPlayerGameResult(player), GameResult.LOSE)
    }

    @Test
    fun `딜러만 블랙잭이면 플레이어가 진다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum20)
        player.startAndReceiveTwoCards(cardsDeckMock)
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(blackJackCards)
        dealer.startAndReceiveTwoCards(cardsDeckMock)

        assertEquals(player.isBlackJack(),false)
        assertEquals(dealer.isBlackJack(),true)
        assertEquals(dealer.getPlayerGameResult(player), GameResult.LOSE)
    }

    @Test
    fun `딜러가 버스트가 아니면서 플레이어 보다 점수가 높으면 플레이어가 진다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum17)
        player.startAndReceiveTwoCards(cardsDeckMock)
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum20)
        dealer.startAndReceiveTwoCards(cardsDeckMock)

        assertEquals(dealer.isBust(),false)
        assertTrue(player.getSumOfValues() < dealer.getSumOfValues())
        assertEquals(dealer.getPlayerGameResult(player), GameResult.LOSE)
    }

    @Test
    fun `둘 다 블랙잭이면 무승부다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(blackJackCards)
        player.startAndReceiveTwoCards(cardsDeckMock)
        dealer.startAndReceiveTwoCards(cardsDeckMock)

        assertEquals(player.isBlackJack(),true)
        assertEquals(dealer.isBlackJack(),true)
        assertEquals(dealer.getPlayerGameResult(player), GameResult.TIE)
    }

    @Test
    fun `둘 다 버스트가 아니면서 동점이면 무승부다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum20)
        player.startAndReceiveTwoCards(cardsDeckMock)
        dealer.startAndReceiveTwoCards(cardsDeckMock)

        assertEquals(player.isBust(),false)
        assertEquals(dealer.isBust(),false)
        assertEquals(player.getSumOfValues(),dealer.getSumOfValues())
        assertEquals(dealer.getPlayerGameResult(player), GameResult.TIE)
    }

    companion object {
        val bustCards =  setOf(
            Card(CardSuit.HEART,CardValue.QUEEN),
            Card(CardSuit.SPADE, CardValue.QUEEN),
            Card(CardSuit.SPADE, CardValue.FIVE))

        val blackJackCards = setOf(
            Card(CardSuit.HEART,CardValue.ACE),
            Card(CardSuit.HEART,CardValue.QUEEN))

        val normalCardsSum20 = setOf(
            Card(CardSuit.SPADE,CardValue.QUEEN),
            Card(CardSuit.HEART,CardValue.QUEEN))

        val normalCardsSum17 = setOf(
            Card(CardSuit.HEART,CardValue.QUEEN),
            Card(CardSuit.HEART,CardValue.SEVEN))
    }
}
