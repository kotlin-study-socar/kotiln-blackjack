package ornn.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CardTest {
    @Test
    fun 게임_운영을_위한_카드덱_생성을_완료하면_카드의_총개수는_52장이다() {
        // when
        val opCards = Cards.getOpCards()

        // then
        assertThat(opCards.size).isEqualTo(52)
    }

    @Test
    fun JQK카드가_존재할때_변환된_숫자정보는_10으로_통일한다() {
        // when
        val scoreOfJ = cardJ.getScoreOfNum()
        val scoreOfQ = cardQ.getScoreOfNum()
        val scoreOfK = cardK.getScoreOfNum()

        // then
        assertThat(scoreOfJ).isEqualTo(10)
        assertThat(scoreOfQ).isEqualTo(10)
        assertThat(scoreOfK).isEqualTo(10)
    }

    @Test
    fun 카드덱에_숫자가_J_Q_K_5인_카드가_있을때_합은_35이다() {
        // given
        val cards = Cards(mutableListOf(cardJ, cardQ, cardK, card5))

        // when
        val sum = cards.getScoreSum()

        // then
        assertThat(sum).isEqualTo(35)
    }

    companion object {
        val cardJ = Card(Shape.SPADE, 'J'.code)
        val cardQ = Card(Shape.SPADE, 'Q'.code)
        val cardK = Card(Shape.SPADE, 'K'.code)
        val card5 = Card(Shape.SPADE, 5)
    }
}
