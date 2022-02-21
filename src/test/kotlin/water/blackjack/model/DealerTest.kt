package water.blackjack.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class DealerTest {
    private val deck = CardsDeck()
    lateinit var dealer: Participant

    @BeforeEach
    fun init() {
        dealer = Dealer()
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
}
