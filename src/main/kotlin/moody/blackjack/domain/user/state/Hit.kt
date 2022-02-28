package moody.blackjack.domain.user.state

import moody.blackjack.domain.card.Cards
import moody.blackjack.domain.card.Deck

class Hit(cards: Cards) : Running(cards) {

    override fun draw(deck: Deck): UserState {
        val cards = getCards()
        cards.add(deck.giveCard())
        return if (cards.isBust()) Bust(cards) else Hit(cards)
    }

    override fun stay(): UserState {
        return Stay(getCards())
    }
}
