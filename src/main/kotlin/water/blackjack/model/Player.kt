package water.blackjack.model

class Player(override val name: String) : Participant() {
    override fun startGame(deck: Deck) {
        super.startGame(deck)
        if (isBlackJack()) {
            updateToStay()
        }
    }

    override fun isHit(): Boolean {
        return (getSumOfValues() < CARD_SUM_LIMIT) && (gameStatus.isHitStatus())
    }
}
