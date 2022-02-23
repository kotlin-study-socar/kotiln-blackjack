package water.blackjack.view

import water.blackjack.exception.BlackJackException
import water.blackjack.exception.ExceptionMessages

object InputView {
    private const val PLAYER_NAME_INPUT_INFO_MESSAGE = "게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)"

    fun getPlayerNames(): List<String> {
        println(PLAYER_NAME_INPUT_INFO_MESSAGE)
        return try {
            val userInput = validatePlayerNameInputExist(readLine())
            validateDuplicatePlayerNames(userInput.split(","))
        } catch (e: BlackJackException){
            println(e.message)
            getPlayerNames()
        }
    }

    fun requestPlayerForOneMoreCard(playerName: String): Boolean {
        println("${playerName}는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)")
        val input = readLine()
        if (input == "y"){
            return true
        }
        return false
    }

    private fun validatePlayerNameInputExist(input: String?) : String {
        if (input?.isEmpty() == true) {
            throw BlackJackException(ExceptionMessages.EMPTY_PLAYER_NAME_MESSAGE)
        }
        return input.toString()
    }

    private fun validateDuplicatePlayerNames(names: List<String>) : List<String> {
        if (names.size != names.toSet().size){
            throw BlackJackException(ExceptionMessages.DUPLICATE_PLAYER_NAME_MESSAGE)
        }
        return names
    }
}
