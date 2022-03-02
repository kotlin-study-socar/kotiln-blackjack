package moody.blackjack.domain.user.state

import moody.blackjack.domain.card.Cards
import moody.blackjack.domain.card.Deck

abstract class Finished(cards: Cards) : Started(cards) {

    override fun draw(deck: Deck): UserState {
        throw IllegalStateException("종료된 상태에서는 카드를 더 받을 수 없습니다.")
    }

    override fun stay(): UserState {
        throw IllegalStateException("종료된 상태에서는 스테이 상태로 변경될 수 없습니다.")
    }

    override fun isFinished(): Boolean {
        return true
    }
}
