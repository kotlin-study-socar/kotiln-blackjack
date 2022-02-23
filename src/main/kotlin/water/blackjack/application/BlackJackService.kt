package water.blackjack.application

import water.blackjack.application.dto.GameResultStringDto
import water.blackjack.application.dto.ParticipantDto
import water.blackjack.application.dto.ParticipantsDto
import water.blackjack.exception.BlackJackException
import water.blackjack.exception.ExceptionMessages
import water.blackjack.model.CardsDeck
import water.blackjack.model.Dealer
import water.blackjack.model.Player

class BlackJackService(private val playerNames: List<String>) {
    private val deck = CardsDeck()
    private val dealer = Dealer()
    private val participants = listOf(dealer) + playerNames.map { Player(it) }

    fun startGame(): List<ParticipantDto> {
        participants.forEach {it.startGame(deck)}
        return ParticipantsDto.convertToParticipantsInfoWithOptionalSum(participants)
    }

    fun getHitPlayers(): List<ParticipantDto> {
        val players = participants.filter { it.isHit() && it !is Dealer }
        return ParticipantsDto.convertToParticipantsInfoWithOptionalSum(players)
    }

    fun offerOneCard(name: String): ParticipantDto {
        return ParticipantDto.convertToParticipantsInfoWithOptionalSum(findByPlayerName(name).also { it.offeredOneCard(deck) })
    }

    fun isHitPlayer(name: String): Boolean {
        return findByPlayerName(name).isHit()
    }

    fun updatePlayerToStay(name: String) {
        findByPlayerName(name).updateToStay()
    }

    fun getParticipants() = ParticipantsDto.convertToParticipantsInfoWithOptionalSum(participants)

    fun getCountsOfUpdatedDealerCards(): Int = dealer.getCountOfAddedCards(deck)

    private fun findByPlayerName(name: String): Player {
        return participants.findLast { it.name == name } as? Player ?: throw BlackJackException(ExceptionMessages.PLAYER_NOT_FOUND_EXCEPTION)
    }

    fun getTotalWinAndLoseResults() : List<GameResultStringDto> {
        val results = mutableListOf<GameResultStringDto>()
        participants.filter { it !is Dealer }.forEach {
            results.add(GameResultStringDto.fromPlayer(it.name, dealer.getPlayerGameResult(it as Player)))
        }
        results.add(0,GameResultStringDto.fromDealer(dealer.name,dealer.getDealerGameResults()))
        return results.toList()
    }
}
