package ornn.service

import io.kotest.core.spec.style.BehaviorSpec
import org.junit.jupiter.api.assertThrows
import ornn.exception.InputIllegalException
import ornn.exception.InputIllegalException.Companion.validateInputIsYesOrNo
import ornn.exception.InputIllegalException.Companion.validateNullInputException

class InputServiceTest : BehaviorSpec({
    given("플레이어 이름을 입력 받았을 때") {
        `when`("공백이 있으면") {
            val input = " "
            then("에러가 발생해야 한다.") {
                assertThrows<InputIllegalException> {
                    validateNullInputException(input)
                }
            }
        }
    }

    given("플레이어가 카드를 더 받을지에 대한 대답을 입력 받았을 때") {
        `when`("y 또는 n이 아닌 다른 값을 입력했을 경우") {
            val input = "K"
            then("에러가 발생해야한다.") {
                assertThrows<InputIllegalException> {
                    validateInputIsYesOrNo(input)
                }
            }
        }
    }
})
