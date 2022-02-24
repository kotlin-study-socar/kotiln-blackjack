package ornn.domain

class Dealer(name: String, cards: Cards) : Player(name, cards) {
    var win: Int = 0
    var lose: Int = 0
    var draw: Int = 0
}
