package moody.blackjack.domain.game

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import moody.blackjack.domain.card.Card
import moody.blackjack.domain.card.Cards
import moody.blackjack.domain.card.Deck
import moody.blackjack.domain.game.WinOrLose.DRAW
import moody.blackjack.domain.game.WinOrLose.LOSE
import moody.blackjack.domain.game.WinOrLose.WIN
import moody.blackjack.domain.user.Dealer
import moody.blackjack.domain.user.Player
import moody.blackjack.domain.user.Users

class GameResultTest : AnnotationSpec() {

    companion object {
        private val 스페이드A = Card.of("스페이드", "A")
        private val 스페이드5 = Card.of("스페이드", "5")
        private val 하트7 = Card.of("하트", "7")
        private val 하트4 = Card.of("하트", "4")
        private val 다이아몬드J = Card.of("다이아몬드", "J")
        private val 다이아몬드3 = Card.of("다이아몬드", "3")
        private val 클로버2 = Card.of("클로버", "2")
        private val 클로버Q = Card.of("클로버", "Q")
        private var deck = Deck { Cards.from(mutableListOf()) }

        private val 무디 = Player("무디")
        private val 오른 = Player("오른")
        private val 워터 = Player("워터")
        private val 딜러 = Dealer()
        private val users = Users(listOf(무디, 오른, 워터, 딜러))
    }

    @BeforeEach
    fun setUp() {
        deck = Deck { Cards.from(mutableListOf(스페이드A, 클로버Q, 다이아몬드3, 하트4, 스페이드5, 하트7, 다이아몬드J, 클로버2)) }
        users.initializeCards(deck)
    }

    @Test
    fun `딜러와 플레이어들을 받아 결과를 저장한다`() {
        // given
        val result = GameResult()

        // when
        result.addPlayersResult(users.getDealer(), users.getPlayers())

        // then
        result.playerResult["무디"] shouldBe WIN
        result.playerResult["오른"] shouldBe LOSE
        result.playerResult["워터"] shouldBe DRAW
    }

    @Test
    fun `딜러가 승리한 횟수를 계산한다`() {
        // given
        val result = GameResult()
        result.addPlayersResult(users.getDealer(), users.getPlayers())

        // when
        val countDealerWin = result.countDealerWin()

        // then
        countDealerWin shouldBe 1
    }

    @Test
    fun `딜러가 패배한 횟수를 계산한다`() {
        // given
        val result = GameResult()
        result.addPlayersResult(users.getDealer(), users.getPlayers())

        // when
        val countDealerLose = result.countDealerLose()

        // then
        countDealerLose shouldBe 1
    }

    @Test
    fun `딜러가 비긴 횟수를 계산한다`() {
        // given
        val result = GameResult()
        result.addPlayersResult(users.getDealer(), users.getPlayers())

        // when
        val countDealerDraw = result.countDealerDraw()

        // then
        countDealerDraw shouldBe 1
    }
}
