package water.blackjack.model.enums

import water.blackjack.exception.BlackjackException
import water.blackjack.exception.ExceptionMessages

enum class GameStatus {
    HIT,
    STAY;

    fun updateStatus(): GameStatus {
        if (isHitStatus()) {
            return STAY
        }
        throw BlackjackException(ExceptionMessages.ALREADY_STAY_STATE_EXCEPTION)
    }

    fun isHitStatus(): Boolean {
        return name == HIT.name
    }
}
