package ornn.domain

class User(name: String, cards: Cards) : Player(name, cards) {
    var result: UserResult = UserResult.BEFORE_GAME
}
