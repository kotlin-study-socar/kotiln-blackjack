package moody.blackjack.domain.card

class Cards private constructor(private val cards: MutableList<Card>) : MutableList<Card> by cards {

    fun isBlackjack(): Boolean {
        return calculateScore().isBlackjack() && cards.size == MINIMUM_SIZE_FOR_BLACKJACK
    }

    fun isBust(): Boolean {
        return calculateScore().isBust()
    }

    fun isLessThen(score: Score): Boolean {
        return calculateScore().isLessThen(score)
    }

    fun calculateScore(): Score {
        return Score.from(
            cards.stream()
                .mapToInt { it.getScore().value }
                .sum()
        )
    }

    companion object {
        private const val MINIMUM_SIZE_FOR_BLACKJACK = 2

        fun from(cards: MutableList<Card>): Cards {
            return Cards(cards)
        }
    }
}
