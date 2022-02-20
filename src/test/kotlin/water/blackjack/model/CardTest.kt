package water.blackjack.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CardTest {

    @Test
    fun `같은 카드 모양(ex 클로버)과 값(ex King, Queen)을 가지면 동일한 카드로 본다`(){
        val card1 = Card(CardSuit.CLOVER, CardValue.KING)
        val card2 = Card(CardSuit.CLOVER, CardValue.KING)
        Assertions.assertEquals(card1,card2)
    }

    @Test
    fun `카드 모양 및 값이 하나라도 다르면 동일한 카드로 보지 않는다`(){
        val card1 = Card(CardSuit.CLOVER, CardValue.KING)
        val card2 = Card(CardSuit.HEART, CardValue.KING)
        val card3 = Card(CardSuit.CLOVER, CardValue.QUEEN)
        Assertions.assertNotEquals(card1,card2)
        Assertions.assertNotEquals(card1,card3)
    }

    @Test
    fun `Ace 카드는 1 또는 11의 값을 가질 수 있다`() {
        val card = Card(CardSuit.CLOVER, CardValue.ACE)
        Assertions.assertEquals(card.getValue(),1)
        Assertions.assertEquals(card.getWithOptionValue(),11)
        Assertions.assertNotEquals(card.getValue(),card.getWithOptionValue())
    }

    @Test
    fun `Ace 외의 카드는 기본 값과 옵션 값을 포함한 값이 항상 동일하다`() {
        val cardValuesExceptAceCard = CardValue.values().filterNot { it == CardValue.ACE }

        cardValuesExceptAceCard.forEach {
            val card = Card(CardSuit.CLOVER,it)
            Assertions.assertNotEquals(it, CardValue.ACE)
            Assertions.assertEquals(card.getValue(),card.getWithOptionValue())
        }
    }
}
