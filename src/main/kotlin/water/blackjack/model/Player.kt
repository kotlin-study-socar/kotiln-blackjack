package water.blackjack.model

import water.blackjack.model.enums.GameStatus

class Player(override val name: String) : Participant() {
    override fun canGetCard(): Boolean {
        return (getSumOfValues() < gameStopSumBoundary) && (gameStatus == GameStatus.HIT)
    }

    fun getOneCard(gameCards: CardsDeck) {
        if (canGetCard()){
            cards.addAll(gameCards.offerCards(ONE_MORE_CARD_COUNT))
        }
    }
}
