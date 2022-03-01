package ornn.domain

abstract class Player(private val name: String, private var cards: Cards) {
    fun getName(): String {
        return name
    }

    fun getCards(): Cards {
        return cards
    }

    fun setCards(cards: Cards) {
        this.cards = cards
    }

    fun takeCard(opCards: Cards) {
        val card = opCards.removeLast()
        cards.add(card)
    }

    fun isCardsSumMoreThanNum(num: Int): Boolean {
        if (getCards().getScoreSum() > num) {
            return true
        }
        return false
    }
}
