package moody.blackjack.ui.view

import moody.blackjack.domain.card.Card
import moody.blackjack.domain.card.Cards
import moody.blackjack.domain.game.GameResult
import moody.blackjack.domain.user.Dealer
import moody.blackjack.domain.user.Player
import moody.blackjack.domain.user.User
import moody.blackjack.domain.user.Users

object OutputView {

    fun printUsersCards(users: Users) {
        users.forEach { printUserCards(it) }
    }

    fun printUserCards(user: User) {
        when (user) {
            is Dealer -> println("딜러 카드: ${formatCard(user.showOneCard())}")
            is Player -> println("${user.name} 카드: ${formatCards(user.getCards())}")
        }
    }

    private fun formatCards(cards: Cards): String {
        return cards.joinToString(", ") { formatCard(it) }
    }

    private fun formatCard(card: Card): String {
        return card.suit.suit + card.denomination.denomination
    }

    fun printError(exception: RuntimeException) {
        println(exception.message)
    }

    fun printPlayerBustMessage() {
        println("플레이어 카드 총합이 21을 넘어 bust 되었습니다.")
    }

    fun printDealerHit() {
        println("\n딜러는 16이하라 한장의 카드를 더 받았습니다.\n")
    }

    fun printUsersCardsAndScore(users: Users) {
        users.forEach {
            println("${it.name} 카드: ${formatCards(it.getCards())} - 결과: ${it.getScore().value}")
        }
    }

    fun printResult(result: GameResult) {
        println("\n최종 승패")
        println("딜러: ${result.countDealerWin()}승 ${result.countDealerDraw()}무 ${result.countDealerLose()}패")
        result.playerResult.entries.forEach {
            println("${it.key}: ${it.value.symbol}")
        }
    }
}
