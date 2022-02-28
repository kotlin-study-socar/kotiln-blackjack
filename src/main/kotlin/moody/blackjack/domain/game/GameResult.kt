package moody.blackjack.domain.game

import moody.blackjack.domain.user.Dealer
import moody.blackjack.domain.user.Player

class GameResult(
    val playerResult: MutableMap<String, WinOrLose> = mutableMapOf(),
) {
    fun addPlayersResult(dealer: Dealer, players: List<Player>) {
        players.forEach { playerResult[it.name] = it.compareWithDealer(dealer) }
    }

    // 플레이어들이 진 횟수가 딜러가 이긴 횟수
    fun countDealerWin() = playerResult.values.count { it == WinOrLose.LOSE }

    fun countDealerLose() = playerResult.values.count { it == WinOrLose.WIN }

    fun countDealerDraw() = playerResult.values.count { it == WinOrLose.DRAW }
}
