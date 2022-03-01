package ornn.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CardTest {

    companion object {
        val cardSpadeJ = Card(Shape.SPADE, 'J'.code)
        val cardSpadeQ = Card(Shape.SPADE, 'Q'.code)
        val cardSpadeK = Card(Shape.SPADE, 'K'.code)
        val cardSpade5 = Card(Shape.SPADE, 5)
        val cardSpadeAce = Card(Shape.SPADE, 1)
        val cardHeartAce = Card(Shape.HEART, 1)
        val cardSpade10 = Card(Shape.SPADE, 10)
    }

    @Test
    fun `게임_운영을_위한_카드덱_생성을_완료하면_카드의_총개수는_52장이다`() {
        // when
        val opCards = Cards.getOpCards()

        // then
        assertThat(opCards.size).isEqualTo(52)
    }

    @Test
    fun `JQK카드가_존재할때_변환된_숫자정보는_10으로_통일한다`() {
        // when
        val scoreOfJ = cardSpadeJ.getScoreOfNum()
        val scoreOfQ = cardSpadeQ.getScoreOfNum()
        val scoreOfK = cardSpadeK.getScoreOfNum()

        // then
        assertThat(scoreOfJ).isEqualTo(10)
        assertThat(scoreOfQ).isEqualTo(10)
        assertThat(scoreOfK).isEqualTo(10)
    }

    @Test
    fun `카드덱에_숫자가_J_Q_K_5인_카드가_있을때_합은_35이다`() {
        // given
        val cards = Cards(mutableListOf(cardSpadeJ, cardSpadeQ, cardSpadeK, cardSpade5))

        // when
        val sum = cards.getScoreSum()

        // then
        assertThat(sum).isEqualTo(35)
    }

    @Test
    fun `카드덱에_ACE가_2장_있을때_합은_12를_반환한다`() {
        // given
        val cards = Cards(mutableListOf(cardSpadeAce, cardHeartAce))

        // when
        val sum = cards.getScoreSum()

        // then
        assertThat(sum).isEqualTo(12)
    }

    @Test
    fun `카드덱에_ACE가_1장_10이_1장_있을때_합은_21을_반환한다`() {
        // given
        val cards = Cards(mutableListOf(cardSpadeAce, cardSpade10))

        // when
        val sum = cards.getScoreSum()

        // then
        assertThat(sum).isEqualTo(21)
    }

    @Test
    fun `카드덱에_10이_1장_ACE가_1장__있을때_합은_21을_반환한다`() {
        // given
        val cards = Cards(mutableListOf(cardSpade10, cardSpadeAce))

        // when
        val sum = cards.getScoreSum()

        // then
        assertThat(sum).isEqualTo(21)
    }
}
