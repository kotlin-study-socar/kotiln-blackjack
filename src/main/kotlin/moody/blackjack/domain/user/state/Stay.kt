package moody.blackjack.domain.user.state

import moody.blackjack.domain.card.Cards

class Stay(cards: Cards) : Finished(cards)
