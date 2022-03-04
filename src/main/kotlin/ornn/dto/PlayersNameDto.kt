package ornn.dto

import ornn.domain.Users

class PlayersNameDto(private val names: List<String>) : List<String> by names {
    companion object {
        fun fromPlayers(users: Users): PlayersNameDto {
            return PlayersNameDto(users.map { it.getName() })
        }
    }
}
