package ornn.domain

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class UserTest {

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
        // given
        val user = User("ornn", Cards(mutableListOf()))
        val opCards = Cards.getAllOpCards()

        // when
        user.takeCard(opCards)

        // then
        assertThat(user.getCards().size).isEqualTo(1)
    }
}
