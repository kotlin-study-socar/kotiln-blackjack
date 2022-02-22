package water.blackjack.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import water.blackjack.exception.BlackJackException
import water.blackjack.exception.ExceptionMessages

class DealerTest {
    private val deck = CardsDeck()
    lateinit var dealer: Dealer

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
}
