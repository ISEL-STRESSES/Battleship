package battleship.model.game

import battleship.model.board.Board
import battleship.model.board.Direction
import battleship.model.board.addShip
import battleship.model.position.Position
import battleship.model.ship.ShipType

data class Game(
    val name: String,
    val boardA: Board,
    val boardB: Board,
    val player: Player = Player.A,
    val turn: Player = Player.A
)

fun Game.put(shipType: ShipType, pos: Position, dir: Direction): Game {
    val newBoard = boardA.addShip(shipType, pos, dir)
    val game = Game(name, newBoard, boardB, turn)
    return game
}
