package water.blackjack.model

abstract class Deck {
    abstract fun offerCards(count: Int): Set<Card>
}
