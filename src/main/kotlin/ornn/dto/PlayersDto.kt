package ornn.dto

import ornn.domain.Users

class PlayersDto(playersDto: List<PlayerDto>) : List<PlayerDto> by playersDto {
    companion object {
        fun fromPlayers(users: Users): PlayersDto {
            return PlayersDto(users.map { PlayerDto(it.getName(), it.getCards()) })
        }
    }
}
