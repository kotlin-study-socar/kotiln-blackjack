package water.blackjack.model.enums

enum class GameResult(val showName: String) {
    WIN("승"),
    LOSE("패"),
    TIE("무승부");

    fun getOppositeResult() = when (this) {
        WIN -> LOSE
        LOSE -> WIN
        TIE -> TIE
    }
}
