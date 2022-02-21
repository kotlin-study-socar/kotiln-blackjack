package water.blackjack.exception

open class BlackJackException(msg: String): RuntimeException(msg)

class OutOfCardException(msg: String): BlackJackException(msg)

class DuplicatePlayerNameException(msg: String): BlackJackException(msg)

class EmptyPlayerNameInputException(msg: String): BlackJackException(msg)

class AlreadyStayStatusException(msg: String): BlackJackException(msg)

class PlayerNotFoundException(msg:String): BlackJackException(msg)
