package moody.blackjack.domain.user

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import moody.blackjack.domain.card.Card
import moody.blackjack.domain.card.Cards
import moody.blackjack.domain.card.Deck
import moody.blackjack.domain.card.Denomination
import moody.blackjack.domain.card.Score
import moody.blackjack.domain.card.Suit
import moody.blackjack.domain.game.WinOrLose.DRAW
import moody.blackjack.domain.game.WinOrLose.LOSE
import moody.blackjack.domain.game.WinOrLose.WIN
import moody.blackjack.domain.user.state.Hit

class UserTest : AnnotationSpec() {

    companion object {
        val 스페이드A = Card.of("스페이드", "A")
        val 스페이드5 = Card.of("스페이드", "5")
        val 하트7 = Card.of("하트", "7")
        val 하트10 = Card.of("하트", "10")
        val 다이아몬드3 = Card.of("다이아몬드", "3")
        val 클로버Q = Card.of("클로버", "Q")
        val 클로버2 = Card.of("클로버", "2")
    }

    @Test
    fun `플레이어를 생성하고 초기 카드 두장(다이아몬드3, 클로버Q)을 덱에서 뽑으면 Hit 상태로 설정된다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(다이아몬드3, 클로버Q)) }
        val player = Player("무디")

        //when
        player.drawInitialCards(deck)

        //then
        player.state.shouldBeTypeOf<Hit>()
        player.getScore() shouldBe Score.from(13)
    }

    @Test
    fun `플레이어(스페이드A, 클로버Q)를 받아 블랙잭 조건이 충족되면 블랙잭 상태를 반환한다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(스페이드A, 클로버Q)) }
        val player = Player("무디")
        player.drawInitialCards(deck)

        //when
        val result = player.isBlackjack()

        //then
        result shouldBe true
    }

    @Test
    fun `플레이어(다이아몬드3, 클로버Q, 하트10)와 딜러(스페이드5, 하트7) 점수를 비교하면 플레이어가 bust이므로 플레이어가 패배한다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(다이아몬드3, 클로버Q, 하트10, 스페이드5, 하트7)) }
        val player = Player("무디")
        player.drawInitialCards(deck)
        player.hit(deck)
        val dealer = Dealer()
        dealer.drawInitialCards(deck)

        //when
        val result = player.compareWithDealer(dealer)

        //then
        result shouldBe LOSE
    }

    @Test
    fun `플레이어(다이아몬드3, 클로버Q)와 딜러(하트10, 스페이드5, 하트7) 점수를 비교하면 딜러가 bust이므로 플레이어가 승리한다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(다이아몬드3, 클로버Q, 하트10, 스페이드5, 하트7)) }
        val player = Player("무디")
        player.drawInitialCards(deck)
        val dealer = Dealer()
        dealer.drawInitialCards(deck)
        dealer.hit(deck)

        //when
        val result = player.compareWithDealer(dealer)

        //then
        result shouldBe WIN
    }

    @Test
    fun `플레이어(다이아몬드3, 클로버Q)와 딜러(하트10, 하트7) 점수를 비교하면 플레이어의 점수가 낮으므로 패배한다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(다이아몬드3, 클로버Q, 하트10, 하트7)) }
        val player = Player("무디")
        player.drawInitialCards(deck)
        val dealer = Dealer()
        dealer.drawInitialCards(deck)

        //when
        val result = player.compareWithDealer(dealer)

        //then
        result shouldBe LOSE
    }

    @Test
    fun `플레이어(하트7, 스페이드5)와 딜러(하트10, 클로버2) 점수를 비교하면 동일하므로 무승부이다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(하트7, 스페이드5, 하트10, 클로버2)) }
        val player = Player("무디")
        player.drawInitialCards(deck)
        val dealer = Dealer()
        dealer.drawInitialCards(deck)

        //when
        val result = player.compareWithDealer(dealer)

        //then
        result shouldBe DRAW
    }

    @Test
    fun `딜러가 카드를 두 장 받고 한장만 보여준다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(다이아몬드3, 클로버Q)) }
        val dealer = Dealer()
        dealer.drawInitialCards(deck)

        //when
        val firstCard = dealer.showOneCard()

        //then
        firstCard.suit shouldBe Suit.DIAMOND
        firstCard.denomination shouldBe Denomination.THREE
    }

    @Test
    fun `딜러가 가진 카드의 총합이 17 미만이면 드로우할 수 있다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(다이아몬드3, 클로버Q)) }
        val dealer = Dealer()
        dealer.drawInitialCards(deck)

        //when
        val result = dealer.isAbleToDraw()

        //then
        result shouldBe true
    }
}
