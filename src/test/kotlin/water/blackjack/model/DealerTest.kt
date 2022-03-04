package water.blackjack.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import water.blackjack.exception.BlackjackException
import water.blackjack.exception.ExceptionMessages
import water.blackjack.model.enums.CardSuit
import water.blackjack.model.enums.CardValue
import water.blackjack.model.enums.GameResult
import water.blackjack.model.mock.DeckMockBuilder

class DealerTest {
    private val deck = ShuffleDeck()
    lateinit var dealer: Dealer
    lateinit var player: Player

    @BeforeEach
    fun init() {
        dealer = Dealer()
        player = Player("player")
    }

    @Test
    fun `딜러가 hit 상태라면 오픈된 한장의 카드만 반환한다`() {
        // when
        dealer.startGame(deck)
        // then
        assertEquals(1, dealer.showCards().size)
    }

    @Test
    fun `딜러가 stay 상태라면 모든 카드를 반환한다`() {
        // given
        dealer.startGame(deck)
        // when
        dealer.updateToStay()
        // then
        assertEquals(2, dealer.showCards().size)
    }

    @Test
    fun `딜러는 마지막에 추가로 뽑은 카트의 개수를 반환한다`() {
        // when
        val countOfAddedCards = dealer.getCountOfAddedCards(deck)
        // then
        assertEquals(dealer.showCards().size, countOfAddedCards)
    }

    @Test
    fun `딜러는 추가 카드를 뽑는 과정을 거치고 난 뒤 Stay 상태가 된다`() {
        // when
        dealer.getCountOfAddedCards(deck)
        // then
        val exception = assertThrows<BlackjackException> {
            dealer.updateToStay()
        }
        assertEquals(ExceptionMessages.ALREADY_STAY_STATE_EXCEPTION, exception.message)
    }

    @Test
    fun `딜러가 버스트라면 플레이어가 버스트여도 플레이어가 이긴다`() {
        // given
        val deckMock = DeckMockBuilder.buildCardsWithParam(bustCloverCards + bustDiamondCards)
        player.startGame(deckMock)
        player.offeredOneCard(deckMock)
        dealer.startGame(deckMock)
        dealer.offeredOneCard(deckMock)
        // when
        val playerGameResult = dealer.getPlayerGameResult(player)
        // then
        assertEquals(true, player.isBust())
        assertEquals(true, dealer.isBust())
        assertEquals(GameResult.WIN, playerGameResult)
    }

    @Test
    fun `딜러와 플레이어 둘 다 블랙잭이면 무승부다`() {
        // given
        player.startGame(DeckMockBuilder.buildCardsWithParam(blackJackHeartCards))
        dealer.startGame(DeckMockBuilder.buildCardsWithParam(blackJackHeartCards))
        // when
        val playerGameResult = dealer.getPlayerGameResult(player)
        // then
        assertEquals(true, player.isBlackJack())
        assertEquals(true, dealer.isBlackJack())
        assertEquals(GameResult.TIE, playerGameResult)
    }

    @Test
    fun `딜러와 플레이어가 동점이면서 둘 다 버스트가 아니면 무승부다`() {
        // given
        player.startGame(DeckMockBuilder.buildCardsWithParam(normalCardsSpadeSum20))
        dealer.startGame(DeckMockBuilder.buildCardsWithParam(normalCardsSpadeSum20))
        // when
        val playerGameResult = dealer.getPlayerGameResult(player)
        // then
        assertEquals(false, player.isBust())
        assertEquals(false, dealer.isBust())
        assertEquals(dealer.getSumOfValues(), player.getSumOfValues())
        assertEquals(GameResult.TIE, playerGameResult)
    }

    @Test
    fun `플레이어가 블랙잭이고 딜러가 블랙잭이 아니라면 플레이어가 이긴다`() {
        // given
        player.startGame(DeckMockBuilder.buildCardsWithParam(blackJackHeartCards))
        dealer.startGame(DeckMockBuilder.buildCardsWithParam(normalCardsSpadeSum20))
        // when
        val playerGameResult = dealer.getPlayerGameResult(player)
        // then
        assertEquals(true, player.isBlackJack())
        assertEquals(false, dealer.isBlackJack())
        assertEquals(GameResult.WIN, playerGameResult)
    }

    @Test
    fun `플레이어가 버스트가 아니면서 플레이어의 점수가 딜러보다 높으면 플레이어가 이긴다`() {
        // given
        player.startGame(DeckMockBuilder.buildCardsWithParam(normalCardsSpadeSum20))
        dealer.startGame(DeckMockBuilder.buildCardsWithParam(normalCardsDiamondSum17))
        // when
        val playerGameResult = dealer.getPlayerGameResult(player)
        // then
        assertEquals(false, player.isBust())
        assertTrue(player.getSumOfValues() > dealer.getSumOfValues())
        assertEquals(GameResult.WIN, playerGameResult)
    }

    @Test
    fun `플레이어만 버스트이고 딜러가 일반 점수라면 플레이어가 진다`() {
        // given
        val deckMock = DeckMockBuilder.buildCardsWithParam(bustCloverCards + normalCardsSpadeSum20)
        player.startGame(deckMock)
        player.offeredOneCard(deckMock)
        dealer.startGame(deckMock)
        // when
        val playerGameResult = dealer.getPlayerGameResult(player)
        // then
        assertEquals(true, player.isBust())
        assertEquals(false, dealer.isBust())
        assertEquals(GameResult.LOSE, playerGameResult)
    }

    @Test
    fun `딜러가 블랙잭이고 플레이어가 블랙잭이 아니라면 플레이어가 진다`() {
        // given
        player.startGame(DeckMockBuilder.buildCardsWithParam(normalCardsSpadeSum20))
        dealer.startGame(DeckMockBuilder.buildCardsWithParam(blackJackHeartCards))
        // when
        val playerGameResult = dealer.getPlayerGameResult(player)
        // then
        assertEquals(false, player.isBlackJack())
        assertEquals(true, dealer.isBlackJack())
        assertEquals(GameResult.LOSE, playerGameResult)
    }

    @Test
    fun `딜러가 버스트가 아니면서 딜러 점수가 플레이어 보다 높으면 플레이어가 진다`() {
        // given
        player.startGame(DeckMockBuilder.buildCardsWithParam(normalCardsDiamondSum17))
        dealer.startGame(DeckMockBuilder.buildCardsWithParam(normalCardsSpadeSum20))
        // when
        val playerGameResult = dealer.getPlayerGameResult(player)
        // then
        assertEquals(false, dealer.isBust())
        assertTrue(player.getSumOfValues() < dealer.getSumOfValues())
        assertEquals(GameResult.LOSE, playerGameResult)
    }

    companion object {
        val bustCloverCards = setOf(
            Card(CardSuit.CLOVER, CardValue.QUEEN),
            Card(CardSuit.CLOVER, CardValue.KING),
            Card(CardSuit.CLOVER, CardValue.FIVE)
        )

        val bustDiamondCards = setOf(
            Card(CardSuit.DIAMOND, CardValue.QUEEN),
            Card(CardSuit.DIAMOND, CardValue.KING),
            Card(CardSuit.DIAMOND, CardValue.FIVE)
        )

        val blackJackHeartCards = setOf(
            Card(CardSuit.HEART, CardValue.ACE),
            Card(CardSuit.HEART, CardValue.QUEEN)
        )

        val normalCardsSpadeSum20 = setOf(
            Card(CardSuit.SPADE, CardValue.QUEEN),
            Card(CardSuit.SPADE, CardValue.KING)
        )

        val normalCardsDiamondSum17 = setOf(
            Card(CardSuit.DIAMOND, CardValue.QUEEN),
            Card(CardSuit.DIAMOND, CardValue.SEVEN)
        )
    }
}
