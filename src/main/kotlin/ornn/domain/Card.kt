package ornn.domain

class Card(private val shape: Shape, private val num: Int) {

    fun getScoreOfNum(): Int {
        return if (isJQK()) 10 else num
    }

    override fun toString(): String {
        return if (num > 10) {
            " ${num.toChar()}${shape.korean}"
        } else {
            " ${num}${shape.korean}"
        }
    }

    private fun isJQK(): Boolean {
        if (num == 'J'.code || num == 'Q'.code || num == 'K'.code) {
            return true
        }
        return false
    }

    fun isACE() = num == 1
}
