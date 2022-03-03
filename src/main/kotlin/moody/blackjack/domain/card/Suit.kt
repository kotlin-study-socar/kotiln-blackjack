package moody.blackjack.domain.card

import java.util.Arrays

enum class Suit(val suit: String) {
    SPADE("스페이드"),
    DIAMOND("다이아몬드"),
    HEART("하트"),
    CLOVER("클로버");

    private fun isMatch(suit: String): Boolean {
        return this.suit == suit
    }

    companion object {
        fun from(suit: String): Suit {
            return Arrays.stream(values())
                .filter { it.isMatch(suit) }
                .findAny()
                .orElseThrow { IllegalArgumentException(suit + "는 유효하지 않은 형태입니다.") }
        }
    }
}
