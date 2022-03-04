package water.blackjack.model.enums

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class CardValueTest {
    @Test
    fun `Ace 값은 1 또는 11의 값을 가질 수 있다`() {
        // given
        val ace = CardValue.ACE

        // then
        assertEquals(1, ace.getValue())
        assertEquals(11, ace.getValue() + ace.getOptionValue())
        assertNotEquals(ace.getValue(), ace.getOptionValue())
    }

    @Test
    fun `Ace 외의 카드는 기본 값과 옵션 값을 포함한 값이 항상 동일하다`() {
        // given
        val cardValuesExceptAceCard = CardValue.values().filterNot { it == CardValue.ACE }

        cardValuesExceptAceCard.forEach {
            // then
            assertNotEquals(CardValue.ACE, it)
            assertEquals(0, it.getOptionValue())
            assertEquals(it.getValue(), it.getValue() + it.getOptionValue())
        }
    }
}
