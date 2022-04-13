package battleship.model.Game

import battleship.model.Position.Position
import battleship.model.Ship.ShipType
import battleship.model.board.Board
import battleship.model.board.Direction
import battleship.model.board.addShip

data class Game(val name: String, val boardA: Board, val boardB: Board, val turn: Player = Player.A)

fun Game.put(shipType: ShipType, pos: Position, dir: Direction): Game {
    val newBoard = boardA.addShip(shipType, pos, dir)
    val game = Game(name, newBoard, boardB, turn)
    return game
}
