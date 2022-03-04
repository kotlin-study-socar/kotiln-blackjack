package ornn.ui

object InputView {
    private const val ASK_NAMES = "게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)"
    private const val ASK_TAKE_MORE_CARD = "는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)"

    fun getUsersName(): String {
        println(ASK_NAMES)
        return readLine().toString()
    }

    fun askUserToTakeMoreCard(name: String): String {
        println(name + ASK_TAKE_MORE_CARD)
        return readLine().toString()
    }
}
