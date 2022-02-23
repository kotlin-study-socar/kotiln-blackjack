package water.blackjack.model

import water.blackjack.model.enums.GameStatus

class Player(override val name: String) : Participant() {
    override fun startGame(deck: CardsDeck) {
        super.startGame(deck)
        if (isBlackJack()) {
            updateToStay()
        }
    }

    override fun isHit(): Boolean {
        return (getSumOfValues() < gameStopSumBoundary) && (gameStatus == GameStatus.HIT)
    }

    fun offeredOneCard(gameCards: CardsDeck) {
        if (isHit()) {
            cards.addAll(gameCards.offerCards(ONE_MORE_CARD_COUNT))
        }
    }
}
