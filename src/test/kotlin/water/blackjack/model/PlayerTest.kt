package water.blackjack.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PlayerTest {
    val deck = CardsDeck()
    lateinit var player: Player

    @BeforeEach
    fun init() {
        player = Player(name = "name")
    }

    @Test
    fun `현재 참여 상태가 STAY 라면 카드를 더 이상 뽑을 수 업다`() {
        player.updateToStay()
        assertEquals(player.isHit(),false)
    }

    @Test
    fun `현재 가진 카드의 총합이 21보다 작고 참여 상태가 HIT 이라면 카드를 뽑을 수 있다`() {
        player.offeredOneCard(deck)
        assertEquals(player.isHit(),true)
    }

    @Test
    fun `카드를 받을 수 있는 상태라면 한 장씩 추가로 뽑을 수 있다`() {
        assertEquals(player.showCards().size,0)
        player.offeredOneCard(deck)
        assertEquals(player.showCards().size,1)
    }
}
