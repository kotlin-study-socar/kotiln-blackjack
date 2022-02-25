package moody.blackjack.domain.user.state

import moody.blackjack.domain.card.Cards
import moody.blackjack.domain.card.Deck

class Hit(cards: Cards) : Running(cards) {

    override fun draw(deck: Deck): UserState {
        getCards().add(deck.giveCard())
        if (getCards().isBust()) {
            return Bust(getCards())
        }
        return Hit(getCards())
    }

    override fun stay(): UserState {
        return Stay(getCards())
    }
}
