package moody.blackjack.domain.user

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import moody.blackjack.domain.card.Card
import moody.blackjack.domain.card.Cards
import moody.blackjack.domain.card.Deck

class UsersTest : AnnotationSpec() {

    companion object {
        private val 스페이드A = Card.of("스페이드", "A")
        private val 스페이드5 = Card.of("스페이드", "5")
        private val 하트7 = Card.of("하트", "7")
        private val 하트10 = Card.of("하트", "10")
        private val 다이아몬드A = Card.of("다이아몬드", "A")
        private val 다이아몬드3 = Card.of("다이아몬드", "3")
        private val 클로버2 = Card.of("클로버", "2")
        private val 클로버Q = Card.of("클로버", "Q")
    }

    @Test
    fun `유저들에게 초기 카드 2장씩 나누어준다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(다이아몬드3, 클로버Q, 하트7, 하트10)) }
        val 무디 = Player("무디")
        val 크로플 = Player("크로플")
        val users = Users(listOf(무디, 크로플))

        //when
        users.initializeCards(deck)

        //then
        users[0].getCards() shouldContainAll listOf(다이아몬드3, 클로버Q)
        users[1].getCards() shouldContainAll listOf(하트7, 하트10)
    }

    @Test
    fun `유저들 중에 진행(running) 상태인 유저가 있다면 true를 반환한다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(다이아몬드3, 클로버Q, 스페이드A, 하트10)) }
        val 무디 = Player("무디")
        val 크로플 = Player("크로플")
        val users = Users(listOf(무디, 크로플))
        users.initializeCards(deck)

        //when
        val result = users.isThereRunningPlayer()

        //then
        result.shouldBeTrue()
    }

    @Test
    fun `유저들 중에 진행(running) 상태인 유저가 없다면 false를 반환한다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(다이아몬드A, 클로버Q, 스페이드A, 하트10)) }
        val 무디 = Player("무디")
        val 크로플 = Player("크로플")
        val users = Users(listOf(무디, 크로플))
        users.initializeCards(deck)

        //when
        val result = users.isThereRunningPlayer()

        //then
        result.shouldBeFalse()
    }


    @Test
    fun `유저들 중에 현재 턴인 플레이어를 반환한다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(클로버2, 클로버Q, 스페이드5, 하트10)) }
        val 무디 = Player("무디")
        val 크로플 = Player("크로플")
        val users = Users(listOf(무디, 크로플))
        users.initializeCards(deck)

        //when
        val result = users.currentPlayer()

        //then
        result shouldBe 무디
    }

    @Test
    fun `유저들 중에 딜러를 제외한 플레이어들을 반환한다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(클로버2, 클로버Q, 스페이드5, 하트10, 다이아몬드A, 다이아몬드3)) }
        val 무디 = Player("무디")
        val 크로플 = Player("크로플")
        val dealer = Dealer()
        val users = Users(listOf(무디, 크로플, dealer))
        users.initializeCards(deck)

        //when
        val result = users.getPlayers()

        //then
        result shouldContainAll listOf(무디, 크로플)
    }

    @Test
    fun `유저들 중에 딜러를 반환한다`() {
        //given
        val deck = Deck { Cards.from(mutableListOf(클로버2, 클로버Q, 스페이드5, 하트10, 다이아몬드A, 다이아몬드3)) }
        val 무디 = Player("무디")
        val 크로플 = Player("크로플")
        val dealer = Dealer()
        val users = Users(listOf(무디, 크로플, dealer))
        users.initializeCards(deck)

        //when
        val result = users.getDealer()

        //then
        result shouldBe dealer
    }
}
