package water.blackjack.model.enums

enum class CardValue(
    val showName: String,
    val mainValue: Int,
    val optionValue: Int = 0
) {
    KING("K",10), QUEEN("Q",10), JACK("J",10),
    ACE("A",1,10),
    TWO("2",2), THREE("3",3), FOUR("4",4),
    FIVE("5",5), SIX("6",6), SEVEN("7",7),
    EIGHT("8",8), NINE("9",9), TEN("10",10),
}
