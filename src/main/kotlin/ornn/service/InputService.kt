package ornn.service

import ornn.exception.InputIllegalException

object InputService {

    fun getUsersName(): List<String> {
        return try {
            val input: String = readLine().toString()
            validateNullInputException(input)
            input.split(",")
        } catch (e: InputIllegalException) {
            println(e.message)
            getUsersName()
        }
    }

    fun validateNullInputException(input: String) {
        if (input == "null" || input.contains(" ") || input.isEmpty()) {
            throw InputIllegalException(Strings.ERROR + Strings.ERROR_NULL_INPUT_EXCEPTION)
        }
    }

    fun isUserToTakeMoreCard(name: String): Boolean {
        println(name + Strings.ASK_TAKE_MORE_CARD)
        val input = readLine().toString()
        return try {
            validateInputIsYesOrNo(input)
            input == "y"
        } catch (e: InputIllegalException) {
            println(e.message)
            isUserToTakeMoreCard(name)
        }
    }

    fun validateInputIsYesOrNo(input: String) {
        if (input != "n" && input != "y" && input != "N" && input != "Y") {
            throw InputIllegalException(Strings.ERROR + Strings.ERROR_NOT_YES_OR_NO_EXCEPTION)
        }
    }
}
