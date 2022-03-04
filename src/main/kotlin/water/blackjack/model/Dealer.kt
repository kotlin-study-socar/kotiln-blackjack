package water.blackjack.model

import water.blackjack.model.enums.GameResult

class Dealer(
    override val name: String = DEALER_NAME,
) : Participant() {
    private val openCard: Card by lazy { cards.iterator().next() }
    private val dealerResults = mutableListOf<GameResult>()

    override fun showCards(): Set<Card> {
        if (gameStatus.isHitStatus()) {
            return hashSetOf(openCard)
        }
        return cards
    }

    override fun isHit(): Boolean {
        val gameStopSumBoundary = DEALER_GAME_STOP_BOUNDARY
        return getSumOfValues() < gameStopSumBoundary
    }

    fun getCountOfAddedCards(deck: Deck): Int {
        var count = 0
        while (isHit()) {
            count++
            offeredOneCard(deck)
        }
        updateToStay()
        return count
    }

    fun getDealerGameResults(): List<GameResult> {
        return dealerResults.toList()
    }

    fun getPlayerGameResult(player: Player): GameResult {
        getDealerGameResult(player).also {
            dealerResults.add(it)
            return it.getOppositeResult()
        }
    }

    private fun getDealerGameResult(player: Player): GameResult {
        val sumOfPlayer = player.getSumOfValues()
        val sumOfDealer = getSumOfValues()

        if (isDealerTieCondition(sumOfDealer, sumOfPlayer, player)) return GameResult.TIE

        if (isDealerLoseCondition(sumOfDealer, sumOfPlayer, player)) return GameResult.LOSE

        // 1. 플레이어만 버스트거나 2. 딜러만 블랙잭이거나 3. 딜러가 플레이어 보다 점수가 높으면 → 딜러가 이긴다
        return GameResult.WIN
    }

    private fun isDealerTieCondition(sumOfDealer: Int, sumOfPlayer: Int, player: Player): Boolean {
        // 1. 둘 다 블랙잭이거나 2. 둘 다 버스트가 아니면서 동점이라면 무승부
        return (isBlackJack() && player.isBlackJack()) || (sumOfPlayer == sumOfDealer && !isBust())
    }

    private fun isDealerLoseCondition(sumOfDealer: Int, sumOfPlayer: Int, player: Player): Boolean {
        // 1. 딜러가 버스트거나 2. 플레이어만 블랙잭이거나 3. 플레이어가 버스트가 아니면서 딜러보다 점수가 높으면 → 딜러가 진다
        return isBust() || player.isBlackJack() || (!player.isBust() && sumOfPlayer > sumOfDealer)
    }

    companion object {
        const val DEALER_NAME = "딜러"
        const val DEALER_GAME_STOP_BOUNDARY = 17
    }
}
