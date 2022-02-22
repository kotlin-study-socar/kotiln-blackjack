package moody.blackjack.domain

fun interface DeckGenerator {
    fun generate(): Cards
}
