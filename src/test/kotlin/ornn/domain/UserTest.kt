package ornn.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ornn.resource.MaxNumber

class UserTest {

    private lateinit var user: User
    private lateinit var deck: Cards
    private val spadeAce = Card(Shape.SPADE, 1)
    private val spade4 = Card(Shape.SPADE, 4)
    private val spade7 = Card(Shape.SPADE, 7)

    @BeforeEach
    fun init() {
        user = User("ornn", Cards(mutableListOf()))
        deck = Cards.getDeck()
    }

    @Test
    fun `유효성검사를_통과한_이름들을_입력_받고_유저들이 생성된다`() {
        // given
        val usersName = listOf("ornn", "water", "moody")

        // when
        val users = Users.fromNames(usersName)

        // then
        assertThat(users.size).isEqualTo(3)
        assertThat(users).first().isInstanceOf(User::class.java)
        assertThat(users).extracting("name", String::class.java)
            .contains("ornn", "water", "moody")
    }

    @Test
    fun `유저는_카드덱에서_카드_한_장을_받을_수_있다`() {
        // when
        user.takeCard(deck)

        // then
        assertThat(user.getCards().size).isEqualTo(1)
    }

    @Test
    fun `유저가_1과_4카드를_받은_상태에서_7을_추가적으로_받았을때_합은_12로_계산되어_더_카드를_받을_수_있다`() {
        // given
        user.setCardsForTest(Cards(mutableListOf(spadeAce, spade4, spade7)))

        // when
        val canTakeMore = user.getCards().getScoreSum() <= MaxNumber.SCORE_MAX

        // then
        assertThat(canTakeMore).isEqualTo(true)
    }
}
