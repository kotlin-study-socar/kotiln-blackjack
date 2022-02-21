package water.blackjack.application

import water.blackjack.application.dto.ParticipantDto
import water.blackjack.application.dto.ParticipantsDto
import water.blackjack.exception.ExceptionMessages
import water.blackjack.exception.PlayerNotFoundException
import water.blackjack.model.CardsDeck
import water.blackjack.model.Dealer
import water.blackjack.model.Player

class BlackJackService(private val playerNames: List<String>) {
    private val deck = CardsDeck()
    private val dealer = Dealer()
    private val participants = listOf(dealer) + playerNames.map { Player(it) }

    fun startGame(): List<ParticipantDto> {
        participants.forEach {it.startAndReceiveTwoCards(deck)}
        return ParticipantsDto.convertNameAndCards(participants)
    }

    fun getPlayersCanHit(): List<ParticipantDto> {
        val players = participants.filter { it.canGetCard() && it !is Dealer }
        return ParticipantsDto.convertNameAndCards(players)
    }

    fun getOneCard(name: String): ParticipantDto {
        return ParticipantDto.convertNameAndCards(findByPlayerName(name).also { it.getOneCard(deck) })
    }

    fun checkPlayerCanGetCard(name: String) :  Boolean {
        return findByPlayerName(name).canGetCard()
    }

    fun updatePlayerToStay(name: String) {
        findByPlayerName(name).updateToStayStatus()
    }

    fun countDealerCardUpdated(): Int = dealer.getCountOfAddedCards(deck)

    private fun findByPlayerName(name: String): Player {
        return participants.findLast { it.name == name } as? Player ?: throw PlayerNotFoundException(ExceptionMessages.PLAYER_NOT_FOUND_EXCEPTION)
    }
}
