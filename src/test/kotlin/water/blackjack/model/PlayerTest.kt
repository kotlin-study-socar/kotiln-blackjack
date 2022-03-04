package water.blackjack.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import water.blackjack.model.enums.CardSuit
import water.blackjack.model.enums.CardValue

class PlayerTest {
    val deck = ShuffleDeck()
    lateinit var player: Player
    val cardsDeckMock = Mockito.mock(ShuffleDeck::class.java)

    @BeforeEach
    fun init() {
        player = Player(name = "name")
    }

    @Test
    fun `맨 처음 받은 카드 두 장으로 블랙잭이 되었다면 게임 시작하자마자 STAY 상태로 변경한다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(blackJackCards)
        player.startGame(cardsDeckMock)
        assertEquals(true, player.isBlackJack())
        assertEquals(21, player.getSumOfValues())
        assertEquals(false, player.isHit())
    }

    @Test
    fun `맨 처음 받은 카드 두 장으로 블랙잭이 안 되었다면 기본적으로 HIT 상태다`() {
        Mockito.`when`(cardsDeckMock.offerCards(2)).thenReturn(normalCardsSum20)
        player.startGame(cardsDeckMock)

        assertEquals(false, player.isBlackJack())
        assertEquals(20, player.getSumOfValues())
        assertEquals(true, player.isHit())
    }

    @Test
    fun `현재 참여 상태가 STAY 라면 카드를 더 이상 뽑을 수 업다`() {
        player.updateToStay()
        assertEquals(false, player.isHit())
    }

    @Test
    fun `현재 가진 카드의 총합이 21보다 작고 참여 상태가 HIT 이라면 카드를 뽑을 수 있다`() {
        player.offeredOneCard(deck)
        assertEquals(true, player.isHit())
    }

    @Test
    fun `카드를 받을 수 있는 상태라면 한 장씩 추가로 뽑을 수 있다`() {
        assertEquals(player.showCards().size, 0)
        player.offeredOneCard(deck)
        assertEquals(1, player.showCards().size)
    }

    companion object {
        val blackJackCards = setOf(
            Card(CardSuit.HEART, CardValue.ACE),
            Card(CardSuit.HEART, CardValue.QUEEN)
        )

        val normalCardsSum20 = setOf(
            Card(CardSuit.SPADE, CardValue.QUEEN),
            Card(CardSuit.HEART, CardValue.QUEEN)
        )
    }
}
