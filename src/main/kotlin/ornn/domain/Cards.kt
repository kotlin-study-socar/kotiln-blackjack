package ornn.domain

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

    fun sumAll(): Int {
        return sumOf { it.getScoreOfNum() }
    }
}
