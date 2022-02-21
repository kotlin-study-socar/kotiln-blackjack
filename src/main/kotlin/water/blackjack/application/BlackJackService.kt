package water.blackjack.application

import water.blackjack.application.dto.ParticipantDto
import water.blackjack.application.dto.ParticipantsDto
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
}
