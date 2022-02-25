package ornn.domain

abstract class Player(private val name: String, private val cards: Cards) {
    fun getName(): String {
        return name
    }

    fun getCards(): Cards {
        return cards
    }

    fun takeCard(opCards: Cards) {
        val card = opCards.removeLast()
        cards.add(card)
    }

    fun isCardsSumMoreThanNum(num: Int): Boolean {
        if (getCards().sumAll() > num) {
            return true
        }
        return false
    }
}
