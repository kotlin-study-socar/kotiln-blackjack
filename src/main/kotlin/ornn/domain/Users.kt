package ornn.domain

class Users(private val players: List<User>) : List<User> by players {

    companion object {
        fun fromNames(usersName: List<String>): Users {
            return Users(usersName.map { User(it, Cards(mutableListOf())) })
        }
    }

    fun takeOneCard(deck: Cards) {
        forEach { it.takeCard(deck) }
    }
}
