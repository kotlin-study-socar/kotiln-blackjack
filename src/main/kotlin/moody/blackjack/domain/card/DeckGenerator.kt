package moody.blackjack.domain.card

fun interface DeckGenerator {
    fun generate(): Cards
}
