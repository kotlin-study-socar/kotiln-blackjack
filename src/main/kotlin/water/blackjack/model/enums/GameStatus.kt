package water.blackjack.model.enums

/*
    hit: 추가 카드를 받을 수 있는 상태
    stay: 추가 카드를 더 이상 받을 수 없는 상태 (바운더리 값을 초과했거나, 받지 않기로 결정한 경우)
 */
enum class GameStatus {
    HIT,
    STAY,
}
