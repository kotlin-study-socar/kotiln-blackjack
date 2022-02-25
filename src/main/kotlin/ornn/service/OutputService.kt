package ornn.service

import Strings
import ornn.domain.Cards
import ornn.domain.Dealer
import ornn.domain.Game
import ornn.domain.Users
import ornn.dto.DealerDto
import ornn.dto.PlayerDto
import ornn.dto.PlayersDto
import ornn.dto.PlayersNameDto

object OutputService {
    fun askName() {
        println(Strings.ASK_NAMES)
    }

    fun printDistriButeTwoCards(dealerDto: DealerDto, playersNameDto: PlayersNameDto) {
        println(
            "\n${dealerDto.name}와 ${playersNameDto.joinToString(", ")} ${Strings.DISTRIBUTE_TWO_CARDS}"
        )
    }

    fun printAllPlayers(findAllPlayers: PlayersDto, dealer: DealerDto) {
        printlnPlayer(dealer)
        findAllPlayers.forEach { printlnPlayer(it) }
        println()
    }

    private fun printPlayer(playerDto: PlayerDto) {
        print("${playerDto.name}카드:")
        printCards(playerDto.cards)
    }

    fun printlnPlayer(playerDto: PlayerDto) {
        printPlayer(playerDto)
        println()
    }

    private fun printCards(cards: Cards) {
        print(cards.joinToString(","))
    }

    fun printAllPlayersWithScore(findAllPlayers: PlayersDto, dealerDto: DealerDto) {
        printPlayer(dealerDto as PlayerDto)
        println("${Strings.CALCULATE_SUM} ${dealerDto.cards.getScoreSum()}")

        findAllPlayers.forEach {
            printPlayer(it)
            println("${Strings.CALCULATE_SUM} ${it.cards.getScoreSum()}")
        }
    }

    fun printDealerTakeMoreCard() {
        println(Strings.NOTICE_DEALER_TAKE_MORE_CARD)
    }

    fun printDealerNotTakeMoreCard() {
        print(Strings.NOTICE_DEALER_NOT_TAKE_MORE_CARD)
    }

    fun printAllPlayersResult(game: Game) {
        println(Strings.RESULT)
        printDealerResult(game.dealer)
        printUsersResult(game.users)
    }

    private fun printDealerResult(dealer: Dealer) {
        println(
            "${dealer.getName()}: " +
                "${dealer.win}승 " +
                "${dealer.draw}무 " +
                "${dealer.lose}패"
        )
    }

    private fun printUsersResult(users: Users) {
        users.forEach { println("${it.getName()}: ${it.result.korean}") }
    }
}
