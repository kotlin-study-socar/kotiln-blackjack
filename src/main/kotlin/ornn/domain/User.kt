package ornn.domain

import ornn.dto.PlayerDto
import ornn.service.InputService
import ornn.service.OutputService

class User(name: String, cards: Cards) : Player(name, cards) {
    var result: String = "경기전"

    fun askedToTakeMoreCard(gameCards: Cards) {
        if (InputService.isUserToTakeMoreCard(getName())) {
            takeCard(gameCards)
            OutputService.printPlayer(PlayerDto.fromPlayer(this))
            println()
            askedToTakeMoreCard(gameCards)
        } else {
            OutputService.printPlayer(PlayerDto.fromPlayer(this))
            println()
        }
    }
}
