package ornn.domain

enum class UserResult(val korean: String) {
    BEFORE_GAME("경기전"),
    WIN("승리"),
    LOSE("패배"),
    DRAW("무승부")
}
