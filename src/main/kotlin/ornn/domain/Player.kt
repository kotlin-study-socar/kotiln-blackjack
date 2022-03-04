package ornn.domain

abstract class Player(private val name: String, private var cards: Cards) {
    fun getName(): String {
        return name
    }

    fun getCards(): Cards {
        return cards
    }

    fun setCardsForTest(cards: Cards) {
        this.cards = cards
    }

    fun takeCard(deck: Cards) {
        val card = deck.removeLast()
        cards.add(card)
    }

    fun isBiggerScoreThan(num: Int) = cards.getScoreSum() > num
}
