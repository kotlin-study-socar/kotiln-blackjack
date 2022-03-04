package water.blackjack.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import water.blackjack.model.enums.CardSuit
import water.blackjack.model.enums.CardValue

class CardTest {

    @Test
    fun `같은 카드 모양(ex 클로버)과 값(ex King, Queen)을 가지면 동일한 카드로 본다`() {
        val card1 = Card(CardSuit.CLOVER, CardValue.KING)
        val card2 = Card(CardSuit.CLOVER, CardValue.KING)
        Assertions.assertEquals(card1, card2)
    }

    @Test
    fun `카드 모양 및 값이 하나라도 다르면 동일한 카드로 보지 않는다`() {
        val card1 = Card(CardSuit.CLOVER, CardValue.KING)
        val card2 = Card(CardSuit.HEART, CardValue.KING)
        val card3 = Card(CardSuit.CLOVER, CardValue.QUEEN)
        Assertions.assertNotEquals(card1, card2)
        Assertions.assertNotEquals(card1, card3)
    }
}
