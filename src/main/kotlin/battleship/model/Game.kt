package battleship.model

import battleship.model.position.Position

data class Fleet(val positions: List<Position> = emptyList(), val player: Player = Player.A, val navy: List<Pair<Position, ShipType>> = emptyList())

class Game(val name: String, val board: Board, val enemyBoard: Board)
