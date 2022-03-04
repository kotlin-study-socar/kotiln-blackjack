package ornn.domain

import ornn.resource.MaxNumber

class Cards(private val cards: MutableList<Card>) : MutableList<Card> by cards {
    companion object {
        fun getDeck(): Cards {
            val cards = mutableListOf<Card>()
            Shape.values().forEach { makingCardOneToJQK(cards, it) }
            cards.shuffle()
            return Cards(cards)
        }

        private fun makingCardOneToJQK(cards: MutableList<Card>, shape: Shape) {
            for (num in 1..10) {
                cards.add(Card(shape, num))
            }
            for (c in listOf('J', 'Q', 'K')) {
                cards.add(Card(shape, c.code))
            }
        }
    }

    fun getScoreSum(): Int {
        var sum = 0
        var size = this.size
        sortByDescending { it.num }
        forEach {
            size--
            sum += getScoreOfNum(it, sum, size)
        }
        return sum
    }

    private fun getScoreOfNum(card: Card, sum: Int, size: Int): Int {
        if (isAceScoreCouldBeEleven(card, sum, size)) {
            return 11
        }
        return card.getScore()
    }

    private fun isAceScoreCouldBeEleven(card: Card, sum: Int, size: Int): Boolean {
        if (card.isACE() && sum + 11 <= MaxNumber.SCORE_MAX && size == 0) {
            return true
        }
        return false
    }
}
