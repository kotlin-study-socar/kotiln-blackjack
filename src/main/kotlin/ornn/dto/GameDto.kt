package ornn.dto

import ornn.domain.Dealer
import ornn.domain.Game
import ornn.domain.Users

class GameDto(val users: Users, val dealer: Dealer) {
    companion object {
        fun fromGame(game: Game): GameDto {
            return GameDto(game.users, game.dealer)
        }
    }
}
