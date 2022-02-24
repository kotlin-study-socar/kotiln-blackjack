package ornn.service

import io.kotest.core.spec.style.BehaviorSpec
import org.junit.jupiter.api.assertThrows
import ornn.exception.InputIllegalException

class InputServiceTest : BehaviorSpec({
    given("플레이어 이름을 입력 받았을 때") {
        val input = " "
        `when`("공백이 있으면") {
            then("에러가 발생해야 한다.") {
                assertThrows<InputIllegalException> {
                    InputService.validateNullInputException(input)
                }
            }
        }
    }
})
