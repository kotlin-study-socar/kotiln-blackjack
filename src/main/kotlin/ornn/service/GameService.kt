package ornn.service

import ornn.domain.Cards
import ornn.domain.Game
import ornn.domain.User
import ornn.domain.UserResult
import ornn.dto.DealerDto
import ornn.dto.PlayersDto
import ornn.dto.PlayersNameDto
import ornn.res.ConstNumbers

class GameService(private val game: Game) {

    private val userService = UserService(game.users)

    fun findDealer(): DealerDto {
        return DealerDto.fromDealer(game.dealer)
    }

    fun findUsers(): PlayersDto {
        return PlayersDto.fromPlayers(game.users)
    }

    fun findUsersName(): PlayersNameDto {
        return PlayersNameDto.fromPlayers(game.users)
    }

    fun distributeTwoCards() {
        ifOpCardIsNullRefillOpCards()
        repeat(2) { game.dealer.takeCard(game.opCards) }
        userService.getTwoCards(game.opCards)
    }

    fun askToUsersTakeMoreCard() {
        ifOpCardIsNullRefillOpCards()
        userService.askedToTakeMoreCard(game.opCards)
        println()
    }

    fun askDealerToTakeMoreCard() {
        ifOpCardIsNullRefillOpCards()
        while (!game.dealer.isCardsSumMoreThanNum(ConstNumbers.DEALER_MAX)) {
            game.dealer.takeCard(game.opCards)
            OutputService.printDealerTakeMoreCard()
        }
        OutputService.printDealerNotTakeMoreCard()
        println()
    }

    fun updateResult() {
        game.users.forEach { checkWhoIsWin(it) }
    }

    private fun checkWhoIsWin(user: User) {
        val dealerScore = game.dealer.getCards().getScoreSum()
        val userScore = user.getCards().getScoreSum()
        val dealerScoreIsMoreThan21 = isScoreMoreThan21(dealerScore)
        val userScoreIsMoreThan21 = isScoreMoreThan21(userScore)

        if (userScoreIsMoreThan21 && dealerScoreIsMoreThan21) {
            draw(user)
        } else if (userScoreIsMoreThan21 && !dealerScoreIsMoreThan21) {
            userLose(user)
        } else if (!userScoreIsMoreThan21 && dealerScoreIsMoreThan21) {
            userWin(user)
        } else {
            compareScore(dealerScore, user)
        }
    }

    private fun isScoreMoreThan21(score: Int) = score > ConstNumbers.SCORE_MAX

    private fun compareScore(dealerScore: Int, user: User) {
        val userScore = user.getCards().getScoreSum()
        if (userScore < dealerScore) {
            userLose(user)
        } else if (userScore > dealerScore) {
            userWin(user)
        } else {
            draw(user)
        }
    }

    private fun userLose(user: User) {
        user.result = UserResult.LOSE
        game.dealer.win++
    }

    private fun draw(user: User) {
        user.result = UserResult.DRAW
        game.dealer.draw++
    }

    private fun userWin(user: User) {
        user.result = UserResult.WIN
        game.dealer.lose++
    }

    fun ifOpCardIsNullRefillOpCards() {
        if (game.opCards.isEmpty()) {
            game.opCards = Cards.getOpCards()
        }
    }
}
