package water.blackjack.view

import water.blackjack.exception.BlackJackException
import water.blackjack.exception.DuplicatePlayerNameException
import water.blackjack.exception.EmptyPlayerNameInputException
import water.blackjack.exception.ExceptionMessages

object InputView {
    private const val PLAYER_NAME_INPUT_INFO_MESSAGE = "게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)"

    fun getPlayerNames(): List<String> {
        println(PLAYER_NAME_INPUT_INFO_MESSAGE)
        return try {
            val userInput = validatePlayerNameInputExist(readLine())
            validateDuplicate(userInput.split(","))
        } catch (e: BlackJackException){
            println(e.message)
            getPlayerNames()
        }
    }

    private fun validatePlayerNameInputExist(input: String?) : String {
        if (input?.isEmpty() == true) {
            throw EmptyPlayerNameInputException(ExceptionMessages.EMPTY_PLAYER_NAME_MESSAGE)
        }
        return input.toString()
    }

    private fun validateDuplicate(names: List<String>) : List<String> {
        if (names.size != names.toSet().size){
            throw DuplicatePlayerNameException(ExceptionMessages.DUPLICATE_PLAYER_NAME_MESSAGE)
        }
        return names
    }
}
