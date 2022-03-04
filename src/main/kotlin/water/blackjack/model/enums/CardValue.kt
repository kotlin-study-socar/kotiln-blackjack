package water.blackjack.model.enums

enum class CardValue(
    val showName: String,
    private val mainValue: Int,
) {
    KING("K", 10), QUEEN("Q", 10), JACK("J", 10),
    ACE("A", 1),
    TWO("2", 2), THREE("3", 3), FOUR("4", 4),
    FIVE("5", 5), SIX("6", 6), SEVEN("7", 7),
    EIGHT("8", 8), NINE("9", 9), TEN("10", 10);

    fun getValue() = mainValue
    fun getOptionValue(): Int {
        if (isAce()) {
            return ACE_OPTION_VALUE
        }
        return NO_OPTION_VALUE
    }
    fun isAce() = (name == ACE.name)

    companion object {
        const val ACE_OPTION_VALUE = 10
        const val NO_OPTION_VALUE = 0
    }
}
