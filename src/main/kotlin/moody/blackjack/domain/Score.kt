package moody.blackjack.domain

class Score private constructor(val value: Int) {

    companion object {
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
