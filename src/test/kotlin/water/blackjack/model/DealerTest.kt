package water.blackjack.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import water.blackjack.exception.BlackjackException
import water.blackjack.exception.ExceptionMessages
import water.blackjack.model.enums.CardSuit
import water.blackjack.model.enums.CardValue
import water.blackjack.model.enums.GameResult

class DealerTest {
    private val deck = Deck()
    lateinit var dealer: Dealer
    lateinit var player: Player
    private val cardsDeckMock = mock(Deck::class.java)

    @BeforeEach
    fun init() {
        dealer = Dealer()
        player = Player("player")
    }

    @Test
    fun `딜러가 hit 상태라면 오픈된 한장의 카드만 반환한다`() {
        dealer.startGame(deck)
        assertEquals(1, dealer.showCards().size)
    }

    @Test
    fun `딜러가 stay 상태라면 모든 카드를 반환한다`() {
        dealer.startGame(deck)
        dealer.updateToStay()
        assertEquals(2, dealer.showCards().size)
    }

    @Test
    fun `딜러는 마지막에 추가로 뽑은 카트의 개수를 반환한다`() {
        val countOfAddedCards = dealer.getCountOfAddedCards(deck)
        assertEquals(dealer.showCards().size, countOfAddedCards)
    }

    @Test
    fun `딜러는 추가 카드를 뽑는 과정을 거치고 난 뒤 Stay 상태가 된다`() {
        dealer.getCountOfAddedCards(deck)
        val exception = assertThrows<BlackjackException> {
            dealer.updateToStay()
        }
        assertEquals(ExceptionMessages.ALREADY_STAY_STATE_EXCEPTION, exception.message)
    }

    @Test
    fun `딜러와 플레이어가 버스트면 플레이어가 이긴다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(bustCards)
        player.startGame(cardsDeckMock)
        dealer.startGame(cardsDeckMock)

        assertEquals(true, player.isBust())
        assertEquals(true, dealer.isBust())
        assertEquals(GameResult.WIN, dealer.getPlayerGameResult(player))
    }

    @Test
    fun `플레이어만 블랙잭이면 플레이어가 이긴다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(blackJackCards)
        player.startGame(cardsDeckMock)
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum20)
        dealer.startGame(cardsDeckMock)

        assertEquals(true, player.isBlackJack())
        assertEquals(false, dealer.isBlackJack())
        assertEquals(GameResult.WIN, dealer.getPlayerGameResult(player))
    }

    @Test
    fun `플레이어가 버스트가 아니면서 딜러보다 점수가 높으면 플레이어가 이긴다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum20)
        player.startGame(cardsDeckMock)
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum17)
        dealer.startGame(cardsDeckMock)

        assertEquals(false, player.isBust())
        assertTrue(player.getSumOfValues() > dealer.getSumOfValues())
        assertEquals(GameResult.WIN, dealer.getPlayerGameResult(player))
    }

    @Test
    fun `플레이어만 버스트라면 플레이어가 진다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(bustCards)
        player.startGame(cardsDeckMock)
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum20)
        dealer.startGame(cardsDeckMock)

        assertEquals(true, player.isBust())
        assertEquals(false, dealer.isBust())
        assertEquals(GameResult.LOSE, dealer.getPlayerGameResult(player))
    }

    @Test
    fun `딜러만 블랙잭이면 플레이어가 진다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum20)
        player.startGame(cardsDeckMock)
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(blackJackCards)
        dealer.startGame(cardsDeckMock)

        assertEquals(false, player.isBlackJack())
        assertEquals(true, dealer.isBlackJack())
        assertEquals(GameResult.LOSE, dealer.getPlayerGameResult(player))
    }

    @Test
    fun `딜러가 버스트가 아니면서 플레이어 보다 점수가 높으면 플레이어가 진다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum17)
        player.startGame(cardsDeckMock)
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum20)
        dealer.startGame(cardsDeckMock)

        assertEquals(false, dealer.isBust())
        assertTrue(player.getSumOfValues() < dealer.getSumOfValues())
        assertEquals(GameResult.LOSE, dealer.getPlayerGameResult(player))
    }

    @Test
    fun `둘 다 블랙잭이면 무승부다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(blackJackCards)
        player.startGame(cardsDeckMock)
        dealer.startGame(cardsDeckMock)

        assertEquals(true, player.isBlackJack())
        assertEquals(true, dealer.isBlackJack())
        assertEquals(GameResult.TIE, dealer.getPlayerGameResult(player))
    }

    @Test
    fun `둘 다 버스트가 아니면서 동점이면 무승부다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum20)
        player.startGame(cardsDeckMock)
        dealer.startGame(cardsDeckMock)

        assertEquals(false, player.isBust())
        assertEquals(false, dealer.isBust())
        assertEquals(dealer.getSumOfValues(), player.getSumOfValues())
        assertEquals(GameResult.TIE, dealer.getPlayerGameResult(player))
    }

    companion object {
        val bustCards = setOf(
            Card(CardSuit.HEART, CardValue.QUEEN),
            Card(CardSuit.SPADE, CardValue.QUEEN),
            Card(CardSuit.SPADE, CardValue.FIVE)
        )

        val blackJackCards = setOf(
            Card(CardSuit.HEART, CardValue.ACE),
            Card(CardSuit.HEART, CardValue.QUEEN)
        )

        val normalCardsSum20 = setOf(
            Card(CardSuit.SPADE, CardValue.QUEEN),
            Card(CardSuit.HEART, CardValue.QUEEN)
        )

        val normalCardsSum17 = setOf(
            Card(CardSuit.HEART, CardValue.QUEEN),
            Card(CardSuit.HEART, CardValue.SEVEN)
        )
    }
}
