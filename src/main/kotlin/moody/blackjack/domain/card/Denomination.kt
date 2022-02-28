package moody.blackjack.domain.card

import java.util.Arrays

enum class Denomination(val denomination: String, val score: Score) {
    TWO("2", Score.from(2)),
    THREE("3", Score.from(3)),
    FOUR("4", Score.from(4)),
    FIVE("5", Score.from(5)),
    SIX("6", Score.from(6)),
    SEVEN("7", Score.from(7)),
    EIGHT("8", Score.from(8)),
    NINE("9", Score.from(9)),
    TEN("10", Score.from(10)),
    JACK("J", Score.from(10)),
    QUEEN("Q", Score.from(10)),
    KING("K", Score.from(10)),
    ACE("A", Score.from(11));

    private fun isMatch(denomination: String): Boolean {
        return this.denomination == denomination
    }

    fun isAce(): Boolean {
        return this == ACE
    }

    companion object {
        fun from(denomination: String): Denomination {
            return Arrays.stream(values())
                .filter { it.isMatch(denomination) }
                .findAny()
                .orElseThrow { IllegalArgumentException(denomination + "는 유효하지 않은 숫자입니다.") }
        }
    }
}
