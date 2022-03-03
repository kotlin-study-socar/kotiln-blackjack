package moody.blackjack

import moody.blackjack.ui.BlackjackController

fun main() {
    val controller = BlackjackController()
    controller.startGame()
    controller.playPlayerRound()
    controller.playDealerRound()
    controller.showResult()
}
