package water.blackjack.model

import water.blackjack.model.enums.GameStatus

class Dealer(
    override val name: String = DEALER_NAME,
    override val gameStopSumBoundary: Int = DEALER_GAME_STOP_BOUNDARY
) : Participant() {
    private val openCard: Card by lazy { cards.random() }

    override fun showCards(): Set<Card> {
        if (gameStatus == GameStatus.HIT){
            return cards.filter { it == openCard }.toSet()
        }
        return cards.toSet()
    }

    companion object {
        const val DEALER_NAME = "딜러"
        const val DEALER_GAME_STOP_BOUNDARY = 17
    }
}
