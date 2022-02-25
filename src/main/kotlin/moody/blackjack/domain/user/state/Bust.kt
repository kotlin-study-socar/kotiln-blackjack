package moody.blackjack.domain.user.state

import moody.blackjack.domain.card.Cards

class Bust(cards: Cards) : Finished(cards)
