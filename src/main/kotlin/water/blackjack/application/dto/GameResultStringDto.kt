package water.blackjack.application.dto

import water.blackjack.model.enums.GameResult

data class GameResultStringDto(val resultMessage: String) {
    companion object {
        fun fromPlayer(playerName: String, gameResultStatus: GameResult): GameResultStringDto {
            return GameResultStringDto("$playerName: ${gameResultStatus.showName}")
        }

        // 반환 예시 → 딜러: 1승 1패
        fun fromDealer(dealerName: String, gameResultStatus: List<GameResult>): GameResultStringDto {
            val message = StringBuilder().append("$dealerName: ")
            gameResultStatus.groupingBy { it.showName }.eachCount().forEach {
                message.append("${it.value}${it.key} ")
            }
            return GameResultStringDto(message.toString())
        }
    }
}
