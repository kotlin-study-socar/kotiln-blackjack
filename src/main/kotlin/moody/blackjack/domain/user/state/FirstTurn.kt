package moody.blackjack.domain.user.state

import moody.blackjack.domain.card.Deck

object FirstTurn {

    fun generateState(deck: Deck): UserState {
        val twoCards = deck.giveTwoCards()
        if (twoCards.isBlackjack()) {
            return Blackjack(twoCards)
        }
        return Hit(twoCards)
    }
}
