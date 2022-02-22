package moody.blackjack.domain

class Cards private constructor(private val cards: MutableList<Card>) : MutableList<Card> by cards {

    fun draw(): Card {
        if (size <= EMPTY_SIZE) {
            throw IllegalArgumentException("카드뭉치에 카드가 없습니다.")
        }
        return cards.removeFirst()
    }

    companion object {
        private const val EMPTY_SIZE = 0

        fun from(cards: MutableList<Card>): Cards {
            return Cards(cards)
        }
    }
}
