package water.blackjack.model

import water.blackjack.model.enums.GameResult
import water.blackjack.model.enums.GameStatus

class Dealer(
    override val name: String = DEALER_NAME,
    override val gameStopSumBoundary: Int = DEALER_GAME_STOP_BOUNDARY
) : Participant() {
    private val openCard: Card by lazy { cards.random() }
    private val dealerResults = mutableListOf<GameResult>()

    override fun showCards(): Collection<Card> {
        val closedCard = cards.filter { it == openCard }
        if (gameStatus == GameStatus.HIT){
            return closedCard
        }
        val openCards = cards.filterNot { it == openCard }
        return closedCard + openCards
    }

    override fun isHit(): Boolean {
        if ( getSumOfValues() < gameStopSumBoundary){
            return true
        }
        return false
    }

    fun getCountOfAddedCards(gameCards: CardsDeck): Int {
        var count = 0
        while (isHit()){
            count++
            cards.addAll(gameCards.offerCards(ONE_MORE_CARD_COUNT))
        }
        updateToStay()
        return count
    }

    fun getDealerGameResults() : List<GameResult> {
        return dealerResults.toList()
    }

    fun getPlayerGameResult(player: Player) : GameResult {
        getDealerGameResult(player).also {
            dealerResults.add(it)
            return it.getOppositeResult()
        }
    }

    private fun getDealerGameResult(player: Player): GameResult {
        val sumOfPlayer = player.getSumOfValues()
        val sumOfDealer = getSumOfValues()

        // 1. 딜러가 버스트거나 2. 플레이어만 블랙잭이거나 3. 플레이어가 버스트가 아니면서 딜러보다 점수가 높으면 => 딜러가 진다
        if (isBust() || (player.isBlackJack() && !isBlackJack()) || (!player.isBust() && sumOfPlayer > sumOfDealer)){
            return GameResult.LOSE
        }
        // 2. 플레이어만 버스트거나 2. 딜러만 블랙잭이거나 3. 딜러가 플레이어 보다 점수가 높으면 => 딜러가 이긴다
        if (player.isBust() || (isBlackJack() && !player.isBlackJack()) || sumOfPlayer < sumOfDealer){
            return GameResult.WIN
        }
        // 그 외의 경우 무승부다 => 1. 둘 다 블랙잭이거나 2. 둘 다 버스트가 아니면서 동점이라면 무승부
        return GameResult.TIE
    }

    companion object {
        const val DEALER_NAME = "딜러"
        const val DEALER_GAME_STOP_BOUNDARY = 17
    }
}
