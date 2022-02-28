package moody.blackjack.domain.user.state

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.types.shouldBeTypeOf
import moody.blackjack.domain.card.Card
import moody.blackjack.domain.card.Cards
import moody.blackjack.domain.card.Deck

class UserStateTest : BehaviorSpec({

    given("기존 카드 두장을 받고 나서") {
        val deck = Deck { Cards.from(mutableListOf(다이아몬드3, 클로버Q, 하트10)) }
        var state = Hit(Cards.from(mutableListOf(스페이드5, 하트7)))

        `when`("Hit 상태로 다이아몬드3 카드를 받아 카드의 총합이 21 이하면") {
            val hit = state.draw(deck)
            then("Hit 상태로 유지된다.") {
                hit.shouldBeTypeOf<Hit>()
            }
        }

        `when`("Stay를 선언하여 카드를 받지 않기로 하면") {
            val stay = state.stay()
            then("Stay 상태로 변경된다.") {
                stay.shouldBeTypeOf<Stay>()
            }
        }

        `when`("Hit 상태로 클로버Q 카드를 받아 카드의 총합이 21을 초과한다면") {
            val bust = state.draw(deck)
            then("Bust 상태로 변경된다.") {
                bust.shouldBeTypeOf<Bust>()
            }
        }
    }

    given("Stay 상태일 때") {
        val deck = Deck { Cards.from(mutableListOf(다이아몬드3, 클로버Q, 하트10)) }
        val stay = Stay(Cards.from(mutableListOf(스페이드5, 하트7)))

        `when`("draw를 시도하면") {
            then("예외를 발생시킨다.") {
                shouldThrow<IllegalStateException> { stay.draw(deck) }
            }
        }

        `when`("stay를 시도하면") {
            then("예외를 발생시킨다.") {
                shouldThrow<IllegalStateException> { stay.stay() }
            }
        }
    }

    given("Bust 상태일 때") {
        val deck = Deck { Cards.from(mutableListOf(다이아몬드3, 클로버Q, 하트10)) }
        val bust = Bust(Cards.from(mutableListOf(스페이드5, 하트7, 하트10)))

        `when`("draw를 시도하면") {
            then("예외를 발생시킨다.") {
                shouldThrow<IllegalStateException> { bust.draw(deck) }
            }
        }

        `when`("stay를 시도하면") {
            then("예외를 발생시킨다.") {
                shouldThrow<IllegalStateException> { bust.stay() }
            }
        }
    }
}) {
    companion object {
        private val 스페이드5 = Card.of("스페이드", "5")
        private val 하트7 = Card.of("하트", "7")
        private val 하트10 = Card.of("하트", "10")
        private val 다이아몬드3 = Card.of("다이아몬드", "3")
        private val 클로버Q = Card.of("클로버", "Q")
    }
}
