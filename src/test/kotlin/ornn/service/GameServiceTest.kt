package ornn.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ornn.domain.Card
import ornn.domain.Cards
import ornn.domain.Game
import ornn.domain.Shape
import ornn.domain.UserResult
import ornn.domain.Users

class GameServiceTest {

    private lateinit var game: Game
    private lateinit var gameService: GameService
    private val spadeAce = Card(Shape.SPADE, 1)
    private val spade10 = Card(Shape.SPADE, 10)
    private val spade9 = Card(Shape.SPADE, 9)
    private val spade2 = Card(Shape.SPADE, 2)

    @BeforeEach
    fun init() {
        game = Game(Users.fromNames(listOf("ornn", "moody", "water")))
        gameService = GameService(game)
    }

    @Test
    fun `게임_운영카드가_모두_소진되면_새로운_카드덱을_발급한다`() {
        // given
        game.deck = Cards(mutableListOf())

        // when
        gameService.ifOpCardIsNullRefilldeck()

        // then
        assertThat(game.deck.size).isEqualTo(52)
    }

    @Test
    fun `게임시작후_모든_플레이어에게_카드_2장씩_배부했을때_플레이어들이_들고있는_카드수는_모두_2개씩이다`() {
        // when
        gameService.distributeTwoCards()

        // then
        assertThat(game.dealer.getCards().size).isEqualTo(2)
        assertThat(game.users).allSatisfy {
            assertThat(it.getCards().size).isEqualTo(2)
        }
        assertThat(game.deck.size).isEqualTo(52 - 4 * 2)
    }

    @Test
    fun `딜러의_점수가_블랙잭_21이고_유저의_점수가21_20_19일때_딜러는_2승_1무_0패이다`() {
        // given
        val dealerCards = mutableListOf(spadeAce, spade10)
        val user1Cards = mutableListOf(spadeAce, spade10)
        val user2Cards = mutableListOf(spade10, spade10)
        val user3Cards = mutableListOf(spade10, spade9)
        game.dealer.setCardsForTest(Cards(dealerCards))
        game.users[0].setCardsForTest(Cards(user1Cards))
        game.users[1].setCardsForTest(Cards(user2Cards))
        game.users[2].setCardsForTest(Cards(user3Cards))

        // when
        gameService.updateResult()

        // then
        assertThat(game.dealer.win).isEqualTo(2)
        assertThat(game.dealer.draw).isEqualTo(1)
        assertThat(game.dealer.lose).isEqualTo(0)
        assertThat(game.users).element(0).extracting("result").contains(UserResult.DRAW)
        assertThat(game.users).element(1).extracting("result").contains(UserResult.LOSE)
        assertThat(game.users).element(2).extracting("result").contains(UserResult.LOSE)
    }

    @Test
    fun `딜러의_점수가_22이고_유저의_점수가22_21_20일때_딜러는_1무_2패이다`() {
        // given
        val dealerCards = mutableListOf(spade10, spade10, spade2)
        val user1Cards = mutableListOf(spade10, spade10, spade2)
        val user2Cards = mutableListOf(spadeAce, spade10)
        val user3Cards = mutableListOf(spade10, spade10)
        game.dealer.setCardsForTest(Cards(dealerCards))
        game.users[0].setCardsForTest(Cards(user1Cards))
        game.users[1].setCardsForTest(Cards(user2Cards))
        game.users[2].setCardsForTest(Cards(user3Cards))

        // when
        gameService.updateResult()

        // then
        assertThat(game.dealer.win).isEqualTo(0)
        assertThat(game.dealer.draw).isEqualTo(1)
        assertThat(game.dealer.lose).isEqualTo(2)
        assertThat(game.users).element(0).extracting("result").contains(UserResult.DRAW)
        assertThat(game.users).element(1).extracting("result").contains(UserResult.WIN)
        assertThat(game.users).element(2).extracting("result").contains(UserResult.WIN)
    }
}
