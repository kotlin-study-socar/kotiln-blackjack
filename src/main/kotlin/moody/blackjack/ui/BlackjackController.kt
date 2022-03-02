package moody.blackjack.ui

import moody.blackjack.domain.card.Deck
import moody.blackjack.domain.card.DeckShuffleGenerator
import moody.blackjack.domain.game.BlackjackGame
import moody.blackjack.domain.user.Dealer
import moody.blackjack.domain.user.Player
import moody.blackjack.domain.user.User
import moody.blackjack.domain.user.Users
import moody.blackjack.ui.view.InputView
import moody.blackjack.ui.view.OutputView

class BlackjackController {
    private val blackjackGame: BlackjackGame

    init {
        val deck = Deck(DeckShuffleGenerator())
        val names = InputView.inputPlayerNames()
        val users = Users((names.map { Player(it) }.plus(Dealer())))
        blackjackGame = BlackjackGame(deck, users)
    }

    fun startGame() {
        blackjackGame.initUserCards()
        OutputView.printUsersCards(blackjackGame.users)
    }

    fun playPlayerRound() {
        while (blackjackGame.hasRemainingUsers()) {
            val currentPlayer = blackjackGame.currentPlayer()
            giveCardToPlayer(currentPlayer)
            if (currentPlayer.isBust()) {
                OutputView.printUserCards(currentPlayer)
                OutputView.printPlayerBustMessage()
                continue
            }
            OutputView.printUserCards(currentPlayer)
        }
    }

    private fun giveCardToPlayer(currentPlayer: User) {
        try {
            blackjackGame.giveCardToPlayer(InputView.inputCallOrStay(currentPlayer.name))
        } catch (e: IllegalArgumentException) {
            OutputView.printError(e)
            giveCardToPlayer(currentPlayer)
        }
    }

    fun playDealerRound() {
        while (blackjackGame.isDealerAbleToDraw()) {
            OutputView.printDealerHit()
            blackjackGame.giveCardToDealer()
        }
    }

    fun showResult() {
        OutputView.printUsersCardsAndScore(blackjackGame.users)
        OutputView.printResult(blackjackGame.calculateResult())
    }
}
