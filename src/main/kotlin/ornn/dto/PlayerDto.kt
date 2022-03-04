package ornn.dto

import ornn.domain.Cards
import ornn.domain.Player

open class PlayerDto(val name: String, val cards: Cards) {
    companion object {
        fun fromPlayer(player: Player): PlayerDto {
            return PlayerDto(player.getName(), player.getCards())
        }
    }
}
