package moody.blackjack.domain.card

class Cards private constructor(private val cards: MutableList<Card>) : MutableList<Card> by cards {

    fun isBlackjack(): Boolean {
        return calculateScore().isTwentyOne() && cards.size == MINIMUM_SIZE_FOR_BLACKJACK
    }

    fun isBust(): Boolean {
        return calculateScore().isBust()
    }

    fun isLessThen(score: Score): Boolean {
        return calculateScore().isLessThen(score)
    }

    fun calculateScore(): Score {
        var totalScore = Score.from(cards.sumOf { it.getScore().value })
        var count = countAce()
        while (count-- > 0) {
            if (!totalScore.isBust()) {
                break
            }
            totalScore = totalScore.convertAceScore()
        }
        return totalScore
    }

    private fun hasAce() = any { it.isAce() }

    private fun countAce() = count { it.isAce() }

    companion object {
        private const val MINIMUM_SIZE_FOR_BLACKJACK = 2

        fun from(cards: MutableList<Card>): Cards {
            return Cards(cards)
        }
    }
}
