package water.blackjack.exception

open class BlackJackException(msg: String) : RuntimeException(msg)

class OutOfCardException(msg: String): BlackJackException(msg)
