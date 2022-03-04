package water.blackjack.application.dto

import water.blackjack.model.Card
import water.blackjack.model.enums.CardSuit
import water.blackjack.model.enums.CardValue

data class CardDto(val suit: CardSuit, val rank: CardValue) {
    companion object {
        fun from(card: Card) = CardDto(card.suit, card.rank)
    }
}
