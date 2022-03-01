package ornn.domain

import ornn.dto.PlayerDto
import ornn.res.ConstNumbers
import ornn.service.InputService
import ornn.service.OutputService

class User(name: String, cards: Cards) : Player(name, cards) {
    var result: UserResult = UserResult.BEFORE_GAME

    fun askedToTakeMoreCard(opCards: Cards) {
        if (this.getCards().getScoreSum() > ConstNumbers.SCORE_MAX) {
            OutputService.printCardsSumIsMoreThanScoreMax()
            return
        }

        if (InputService.isUserToTakeMoreCard(getName())) {
            takeCard(opCards)
            OutputService.printlnPlayer(PlayerDto.fromPlayer(this))
            askedToTakeMoreCard(opCards)
        } else {
            OutputService.printlnPlayer(PlayerDto.fromPlayer(this))
        }
    }
}
