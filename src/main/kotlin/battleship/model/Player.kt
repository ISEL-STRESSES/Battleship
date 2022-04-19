package battleship.model

enum class Player(val id: Int) {
    A(1), B(2);

    fun other() = if (this === A) B else A
}
