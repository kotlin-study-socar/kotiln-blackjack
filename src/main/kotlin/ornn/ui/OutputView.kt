package ornn.ui

import ornn.domain.Card
import ornn.domain.Cards
import ornn.domain.Dealer
import ornn.domain.User
import ornn.domain.Users
import ornn.dto.DealerDto
import ornn.dto.GameDto
import ornn.dto.PlayerDto
import ornn.dto.PlayersNameDto
import ornn.dto.UsersDto

object OutputView {
    private const val DISTRIBUTE_TWO_CARDS = "에게 2장을 배부합니다."
    private const val NOTICE_DEALER_TAKE_MORE_CARD = "딜러는 합산 16이하라서 한장의 카드를 더 받았습니다."
    private const val NOTICE_DEALER_NOT_TAKE_MORE_CARD = "딜러는 합산점수가 16 초과여서 더 이상의 카드를 더 받지않습니다.\n"
    private const val NOTICE_CARDS_SUM_IS_MORE_THAN_SCORE_MAX = "플레이어의 카드합이 21이 넘었습니다. 더이상 카드를 받지 않습니다."
    private const val CALCULATE_SUM = " - 합산결과: "
    private const val RESULT = "\n## 최종 승패"

    fun printDistriButingTwoCards(dealerDto: DealerDto, playersNameDto: PlayersNameDto) {
        println(
            "\n${dealerDto.name}와 ${playersNameDto.joinToString(", ")} $DISTRIBUTE_TWO_CARDS"
        )
    }

    private fun cardFormat(card: Card): String {
        return when {
            card.num == 1 -> "A"
            card.isJQK() -> " ${card.num.toChar()}"
            else -> " ${card.num}"
        } + card.shape.korean
    }

    private fun cardsFormat(cards: Cards): String {
        return cards.joinToString(",") { cardFormat(it) }
    }

    fun printAllPlayers(findAllPlayers: UsersDto, dealer: DealerDto) {
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
        print(cardsFormat(cards))
    }

    fun printAllPlayersWithScore(findAllPlayers: UsersDto, dealerDto: DealerDto) {
        printPlayer(dealerDto as PlayerDto)
        println("$CALCULATE_SUM ${dealerDto.cards.getScoreSum()}")

        findAllPlayers.forEach {
            printPlayer(it)
            println("$CALCULATE_SUM ${it.cards.getScoreSum()}")
        }
    }

    fun printDealerTakeMoreCard() {
        println(NOTICE_DEALER_TAKE_MORE_CARD)
    }

    fun printDealerNotTakeMoreCard() {
        print(NOTICE_DEALER_NOT_TAKE_MORE_CARD)
    }

    fun printAllPlayersResult(gameDto: GameDto) {
        println(RESULT)
        printDealerResult(gameDto.dealer)
        printUsersResult(gameDto.users)
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
        users.forEach { println(userResultFormat(it)) }
    }

    private fun userResultFormat(user: User): String {
        return "${user.getName()}: ${user.result.korean}"
    }

    fun printCardsSumIsMoreThanScoreMax() {
        println(NOTICE_CARDS_SUM_IS_MORE_THAN_SCORE_MAX)
    }
}
