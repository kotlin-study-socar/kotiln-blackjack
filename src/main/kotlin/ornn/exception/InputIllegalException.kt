package ornn.exception

class InputIllegalException(override val message: String) : IllegalArgumentException(message) {
    companion object {
        private const val ERROR = "[ERROR] "
        private const val ERROR_NULL_INPUT_EXCEPTION = "공백을 제외한 이름을 입력해주세요"
        private const val ERROR_NOT_YES_OR_NO_EXCEPTION = "y와 n 둘중 하나를 입력해주세요"

        fun validateNullInputException(input: String) {
            if (input == "null" || input.contains(" ") || input.isEmpty()) {
                throw InputIllegalException(ERROR + ERROR_NULL_INPUT_EXCEPTION)
            }
        }

        fun validateInputIsYesOrNo(input: String) {
            val lowerCaseInput = input.lowercase()
            if (lowerCaseInput != "n" && lowerCaseInput != "y") {
                throw InputIllegalException(ERROR + ERROR_NOT_YES_OR_NO_EXCEPTION)
            }
        }
    }
}
