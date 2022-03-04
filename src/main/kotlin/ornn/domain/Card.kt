package ornn.domain

class Card(val shape: Shape, val num: Int) {

    fun getScore(): Int {
        return if (isJQK()) 10 else num
    }

    fun isJQK(): Boolean {
        if (num == 'J'.code || num == 'Q'.code || num == 'K'.code) {
            return true
        }
        return false
    }

    fun isACE() = num == 1
}
