package battleship.model

import battleship.model.GameState.*
import battleship.model.board.*
import battleship.model.ship.ShipType

/**
 * Keep the current state of the game.
 * @property SETUP setup stage where only PUT commands will be allowed;
 * @property FIGHT fight stage where you can do all the commands except the ones in the [SETUP] phase;
 * @property OVER game over stage meaning you can't do any commands.
 */
enum class GameState {
    SETUP, FIGHT, OVER
}

enum class PutError { NONE, INVALID_POSITION, INVALID_ARGUMENTS }


enum class PlayError { NONE, INVALID_TURN, GAME_OVER }

data class PlayResult(val game: Game, val error: PutError)


data class Game(
    val name: String,
    val boardA: Board,
    val boardB: Board? = null,
    val state: GameState = SETUP,
    val player: Player = Player.A,
    val turn: Player = Player.A
)


fun Game.putShip(type: ShipType, pos: Position, direction: Direction): Game {
    check(this.state == SETUP) { "Cant change fleet after game started" }

    val newBoard = boardA.putShip(type, pos, direction)

    return this.copy(boardA = newBoard)
}


fun Game.putAllShips() {
    //TODO
//    while(true){
//        val pos = Position.values.random()
//        val currShip = ShipType.values.forEach {
//            super.hiputS
//        }
//
//        this.putShip(currShip, pos)
//    }
}

fun createGame(): Game {
    return Game("", Board(), Board())
}

/*
fun Game.placeShip(type: ShipType, position: Position): Game
{

}

 */
