package model

import model.position.Position

data class Doc(val _id: String, val content: List<String>)

data class Fleet(val positions: List<Position> = emptyList(), val player: Player = Player.A, val navy: List<Pair<Position, ShipType>> = emptyList())

class Game(val name: String, val board: Board, val enemy: Board)

val initialGame = Game("test",)
