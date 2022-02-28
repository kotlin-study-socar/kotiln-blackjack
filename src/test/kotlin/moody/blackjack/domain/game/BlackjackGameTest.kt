package moody.blackjack.domain.game

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import moody.blackjack.domain.card.Card
import moody.blackjack.domain.card.Cards
import moody.blackjack.domain.card.Deck
import moody.blackjack.domain.user.Player
import moody.blackjack.domain.user.Users
import moody.blackjack.domain.user.state.Hit
import moody.blackjack.domain.user.state.Stay

class BlackjackGameTest : AnnotationSpec() {

    companion object {
        private const val CALL = "y"
        private const val STAY = "n"

        val 스페이드5 = Card.of("스페이드", "5")
        val 하트7 = Card.of("하트", "7")
        val 클로버2 = Card.of("클로버", "2")
        val 무디 = Player("무디")
    }

    @Test
    fun `플레이어가 y 응답을 하면 hit을 실행한다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(스페이드5, 하트7, 클로버2)) }
        val game = BlackjackGame(deck, Users(listOf(무디)))
        game.initUserCards()
        val answer = CALL

        //when
        game.giveCardToPlayer(answer)

        //then
        game.currentPlayer() shouldBe 무디
        game.currentPlayer().state.shouldBeTypeOf<Hit>()
        game.currentPlayer().getCards() shouldContainAll listOf(스페이드5, 하트7, 클로버2)
    }

    @Test
    fun `플레이어가 n 응답을 하면 stay를 실행한다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(스페이드5, 하트7, 클로버2)) }
        val game = BlackjackGame(deck, Users(listOf(무디)))
        game.initUserCards()
        val answer = STAY

        //when
        game.giveCardToPlayer(answer)

        //then
        val moody = game.users.getPlayers()[0]
        moody shouldBe 무디
        moody.state.shouldBeTypeOf<Stay>()
        moody.getCards() shouldContainAll listOf(스페이드5, 하트7)
    }
}
