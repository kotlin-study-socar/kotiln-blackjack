package moody.blackjack.domain.card

class Score private constructor(val value: Int) {

    fun plus(score: Score): Score {
        return from(this.value + score.value)
    }

    fun isTwentyOne(): Boolean {
        return value == TWENTY_ONE
    }

    fun isBust(): Boolean {
        return value > TWENTY_ONE
    }

    fun isBiggerThen(score: Score) = this.value > score.value

    fun isLessThen(score: Score) = this.value < score.value

    companion object {
        private const val TWENTY_ONE = 21

        fun from(value: Int): Score {
            return Score(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Score

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value
    }
}
