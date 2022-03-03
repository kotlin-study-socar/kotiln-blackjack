package water.blackjack.model

import water.blackjack.model.enums.GameStatus

class Player(override val name: String) : Participant() {
    override fun startGame(deck: Deck) {
        super.startGame(deck)
        if (isBlackJack()) {
            updateToStay()
        }
    }

    override fun isHit(): Boolean {
        return (getSumOfValues() < CARD_SUM_LIMIT) && (gameStatus == GameStatus.HIT)
    }

    fun offeredOneCard(gameCards: Deck) {
        if (isHit()) {
            cards.addAll(gameCards.offerCards(ONE_MORE_CARD_COUNT))
        }
    }
}
