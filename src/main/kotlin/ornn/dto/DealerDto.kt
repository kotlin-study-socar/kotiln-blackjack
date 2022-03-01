package ornn.dto

import ornn.domain.Cards
import ornn.domain.Dealer

class DealerDto(name: String, cards: Cards, val win: Int, val lose: Int, val draw: Int) : PlayerDto(name, cards) {
    companion object {
        fun fromDealer(dealer: Dealer): DealerDto {
            return DealerDto(
                dealer.getName(), dealer.getCards(),
                dealer.win, dealer.lose, dealer.draw
            )
        }
    }
}
