package ornn.service

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
        repeat(2) { game.dealer.takeCard(game.opCards) }
        userService.getTwoCards(game.opCards)
    }

    fun askToUsersTakeMoreCard() {
        userService.askedToTakeMoreCard(game.opCards)
        println()
    }

    fun askDealerToTakeMoreCard() {
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

        if (isScoreMoreThan21(userScore) && isScoreMoreThan21(dealerScore)) {
            draw(user)
        } else if (isScoreMoreThan21(userScore) && !isScoreMoreThan21(dealerScore)) {
            userLose(user)
        } else if (!isScoreMoreThan21(userScore) && isScoreMoreThan21(dealerScore)) {
            userWin(user)
        } else {
            compareScore(userScore, dealerScore, user)
        }
    }

    private fun isScoreMoreThan21(score: Int) = score > ConstNumbers.SCORE_MAX

    private fun compareScore(userScore: Int, dealerScore: Int, user: User) {
        if (userScore < dealerScore) {
            userLose(user)
        } else if (userScore > dealerScore) {
            userWin(user)
        } else {
            draw(user)
        }
    }

    private fun userWin(user: User) {
        user.result = UserResult.LOSE
        game.dealer.win++
    }

    private fun draw(user: User) {
        user.result = UserResult.DRAW
        game.dealer.draw++
    }

    private fun userLose(user: User) {
        user.result = UserResult.WIN
        game.dealer.lose++
    }
}
