package ornn.domain

class Game(val users: Users) {
    val dealer = Dealer("딜러", Cards(mutableListOf()))
    var deck = Cards.getDeck()
}
