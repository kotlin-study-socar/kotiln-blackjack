package moody.blackjack.domain.card

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class CardsTest : AnnotationSpec() {

    companion object {
        private val 스페이드A = Card.of("스페이드", "A")
        private val 스페이드10 = Card.of("스페이드", "10")
        private val 스페이드7 = Card.of("스페이드", "7")
        private val 하트10 = Card.of("하트", "10")
        private val 클로버2 = Card.of("클로버", "2")
    }

    @Test
    fun `cards의 점수 총합이 21이고 단 두장만 있을 때 blackjack이 된다`() {
        // given
        val cards = Cards.from(mutableListOf(스페이드A, 하트10))

        // when
        val result = cards.isBlackjack()

        // then
        result shouldBe true
    }

    @Test
    fun `cards의 점수 총합이 21을 초과하면 bust가 된다 `() {
        // given
        val cards = Cards.from(mutableListOf(스페이드10, 하트10, 클로버2))

        // when
        val result = cards.isBust()

        // then
        result shouldBe true
    }

    @Test
    fun `cards의 점수 총합을 구한다`() {
        // given
        val cards = Cards.from(mutableListOf(스페이드7, 하트10))

        // when
        val result = cards.calculateScore()

        // then
        result shouldBe Score.from(17)
    }

    @Test
    fun `cards의 점수가 21을 넘었을 때 에이스가 있으면 1점으로 계산해 총합을 구한다`() {
        // given
        val cards = Cards.from(mutableListOf(스페이드A, 스페이드10, 하트10))

        // when
        val result = cards.calculateScore()

        // then
        result shouldBe Score.from(21)
    }
}
