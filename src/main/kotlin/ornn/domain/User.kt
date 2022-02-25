package ornn.domain

import ornn.dto.PlayerDto
import ornn.service.InputService
import ornn.service.OutputService

class User(name: String, cards: Cards) : Player(name, cards) {
    var result: String = "경기전"

    fun askedToTakeMoreCard(opCards: Cards) {
        if (InputService.isUserToTakeMoreCard(getName())) {
            takeCard(opCards)
            OutputService.printlnPlayer(PlayerDto.fromPlayer(this))
            askedToTakeMoreCard(opCards)
        } else {
            OutputService.printlnPlayer(PlayerDto.fromPlayer(this))
        }
    }
}
