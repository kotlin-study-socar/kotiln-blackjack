package ornn.dto

import ornn.domain.Users

class UsersDto(playersDto: List<PlayerDto>) : List<PlayerDto> by playersDto {
    companion object {
        fun fromUsers(users: Users): UsersDto {
            return UsersDto(users.map { PlayerDto(it.getName(), it.getCards()) })
        }
    }
}
