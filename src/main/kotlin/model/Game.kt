package model

import model.position.Position

data class Fleet(val army: List<Position>, val player: Player)

class Game(val name: String, val boardA: Fleet, val boardB: Fleet)

val initialGame = Game("", Fleet(emptyList(), Player.A), Fleet(emptyList(), Player.B))
