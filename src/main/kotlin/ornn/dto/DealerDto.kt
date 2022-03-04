package ornn.dto

import ornn.domain.Cards
import ornn.domain.Dealer

class DealerDto(name: String, cards: Cards) : PlayerDto(name, cards) {
    companion object {
        fun fromDealer(dealer: Dealer): DealerDto {
            return DealerDto(dealer.getName(), dealer.getCards())
        }
    }
}
