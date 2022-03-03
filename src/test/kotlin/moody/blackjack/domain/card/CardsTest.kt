package moody.blackjack.domain.card

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class CardsTest : AnnotationSpec() {

    companion object {
        private val 스페이드A = Card.of("스페이드", "A")
        private val 스페이드10 = Card.of("스페이드", "10")
        private val 스페이드7 = Card.of("스페이드", "7")
        private val 하트10 = Card.of("하트", "10")
        private val 하트A = Card.of("하트", "A")
        private val 다이아몬드A = Card.of("다이아몬드", "A")
        private val 클로버A = Card.of("클로버", "A")
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

    @Test
    fun `cards에 ACE가 2장 이상일 때 bust가 될 점수라면 여분의 ACE들을 1점으로 계산해서 총합을 구한다`() {
        // given
        val cards = Cards.from(mutableListOf(스페이드A, 다이아몬드A, 하트A, 클로버A))

        // when
        val result = cards.calculateScore()

        // then
        result.value shouldBe 14
    }
}
