package ornn.domain

import ornn.res.ConstNumbers

class Cards(private val cards: MutableList<Card>) : MutableList<Card> by cards {
    companion object {
        private val jqkList = listOf('J', 'Q', 'K')

        fun getOpCards(): Cards {
            val cards = mutableListOf<Card>()
            Shape.values().forEach { makingCardOneToJQK(cards, it) }
            cards.shuffle()
            return Cards(cards)
        }

        private fun makingCardOneToJQK(cards: MutableList<Card>, shape: Shape) {
            for (num in 1..10) {
                cards.add(Card(shape, num))
            }
            for (c in jqkList) {
                cards.add(Card(shape, c.code))
            }
        }
    }

    fun getScoreSum(): Int {
        var sum = 0
        var size = this.size
        sortByDescending { it.getNum() }
        forEach {
            size--
            sum += getEffectiveScore(it, sum, size)
        }

        return sum
    }

    private fun getEffectiveScore(card: Card, sum: Int, size: Int): Int {
        if (card.isACE() && sum + 11 <= ConstNumbers.SCORE_MAX && size == 0) {
            return 11
        }
        return card.getScoreOfNum()
    }
}
