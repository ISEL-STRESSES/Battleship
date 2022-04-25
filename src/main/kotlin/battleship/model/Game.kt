package battleship.model

import battleship.model.GameState.*
import battleship.model.board.*
import battleship.model.ship.ShipType
import battleship.storage.Storage

// enum class PutError { NONE, INVALID_POSITION, INVALID_ARGUMENTS }

enum class PlayError { NONE, INVALID_SHOT, INVALID_TURN, GAME_OVER }

//
data class PlayResult(val game: Game, val errors: Pair<ShotConsequence, PlayError>)

/**
 * Keep the current state of the game.
 * @property SETUP setup stage where only PUT commands will be allowed;
 * @property FIGHT fight stage where you can do all the commands except the ones in the [SETUP] phase;
 * @property OVER game over stage meaning you can't do any commands.
 */
enum class GameState {
    SETUP, FIGHT, OVER
}

/**
 * Central object that represents the battleship game
 * @property name name of the game
 * @property boardA [Board] of the first player
 * @property boardB [Board] of the second player
 * @property state current [GameState]
 * @property player game [Player]
 * @property turn current [Player] to make a turn.
 */
data class Game(
    val name: String,
    val boardA: Board,
    val boardB: Board? = null,
    val state: GameState = SETUP,
    val player: Player = Player.A,
    val turn: Player = Player.A
)

/**
 *
 */
fun Game.startGame(gameName: String, st: Storage): Game {
    TODO("Not yet implemented")
}

/**
 *
 */
fun GameState.checkState(wantedState: GameState) {
    when (wantedState) {
        SETUP -> check(wantedState != this) { "Can't change fleet after game started" }
        FIGHT -> check(wantedState != this) { "Can't make a shot before start" }
        OVER -> check(wantedState != this) { "Game Over, FATALITY" }
    }
}

/**
 * [Game] Function that will put a ship if it's a valid command and ship
 * @param type [ShipType] of the ship to put
 * @param pos head [Position] of the ship
 * @param dir [Direction] of the ship
 * @return updated [Game]
 */
fun Game.putShip(type: ShipType, pos: Position, dir: Direction): Game {
    state.checkState(SETUP) // Verify you're in the right state for put command

    val newBoard = boardA.putShip(type, pos, dir)
    return this.copy(boardA = newBoard)
}

/**
 *[Game] Function that will remove a ship if it's a valid command and ship
 * @param pos [Position] to remove the ship from
 * @return updated [Game]
 */
fun Game.removeShip(pos: Position): Game {

    val newBoard = boardA.removeShip(pos)
    if (boardA === newBoard) error("No ship in $pos")
    return this.copy(boardA = newBoard)
}

/**
 *[Game] Function that will remove all ships if it's a valid command
 * @return updated [Game]
 */
fun Game.removeAll(): Game {
    return this.copy(boardA = Board())
}

// fun Game.getTarget(pos :Position) : Game {
//    check(state == FIGHT) { "Cant change fleet after game started" }
//
//    val newBoard = boardB?.getTarget()
//    return copy(boardB= newBoard)
// }

fun Game.putAllShips() {
     TODO()
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

fun startGame(name: String, st: Storage): Game {
    TODO()
}

/*
fun Game.placeShip(type: ShipType, position: Position): Game
{

}

 */

enum class ShotConsequence {
    MISS, HIT, SUNK, INVALID
}

/**
 *
 */
fun Game.getPlayerBoard(target: Player) = if (target == Player.A) boardA else boardB

// TODO() FAZER A MESMA COISA PARA O PUT

fun Game.makeShot(pos: Position): PlayResult {
    if (player == turn) {
        val enemyBoard = getPlayerBoard(player.other())
        checkNotNull(enemyBoard) // TODO ("gambiarra") //não deveria ser preciso pois já verificámos que estamos no "FIGHT_STATE" logo boardB!=null

        val boardResult = enemyBoard.makeShot(pos)

        return if (boardResult.board != enemyBoard) {
            // Check what board changed
            if (enemyBoard == boardA)
                PlayResult(
                    copy(boardA = boardResult.board),
                    Pair(boardResult.consequence, boardResult.error)
                ) // TODO ("bota mais gambiarra")
            else
                PlayResult(copy(boardB = boardResult.board), Pair(boardResult.consequence, boardResult.error))
        } else {
            PlayResult(this, Pair(boardResult.consequence, boardResult.error))
        }
    }
    return PlayResult(
        this,
        Pair(ShotConsequence.INVALID, PlayError.INVALID_TURN)
    ) // if not your turn don't change the game
}

fun Game.checkWin(): GameState = if (boardA.win() || boardB?.win() ?: throw IllegalStateException()) OVER else FIGHT
