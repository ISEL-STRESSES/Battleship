package model

enum class Player(val symbol: Char) {
    A('X'), B('O');

    fun other() = if (this === A) B else A
}
