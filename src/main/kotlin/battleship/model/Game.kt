package battleship.model

import battleship.model.GameState.*
import battleship.model.board.Board
import battleship.model.ship.Ship
import battleship.model.ship.ShipType
import battleship.model.board.position.Position

/**
 * Keep the current state of the game.
 * @property SETUP setup stage where only PUT commands will be allowed;
 * @property FIGHT fight stage where you can do all the commands except the ones in the [SETUP] phase;
 * @property OVER game over stage meaning you can't do any commands.
 */
enum class GameState {
    SETUP, FIGHT, OVER
}

data class Game(
    val name: String,
    val boardA: Board,
    val boardB: Board,
    val state: GameState = SETUP,
    val player: Player = Player.A,
    val turn: Player = Player.A
)

fun createGame(): Game {
    return Game("", Board(), Board())
}

/*
fun Game.placeShip(type: ShipType, position: Position): Game
{

}

 */

// fun Game.put(shipType: ShipType, pos: Position): Game {
//    val newBoard = boardA.addShip(shipType, pos)
//    val game = Game(name, newBoard, boardB, turn)
//    return game
// }
