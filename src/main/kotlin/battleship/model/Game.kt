package battleship.model

import battleship.model.GameState.*
import battleship.model.board.Board
import battleship.model.board.Direction
import battleship.model.board.Position
import battleship.model.board.ShotConsequence
import battleship.model.board.makeShot
import battleship.model.board.putShip
import battleship.model.board.removeShip
import battleship.model.ship.ShipType
import battleship.storage.Storage

// enum class PutError { NONE, INVALID_POSITION, INVALID_ARGUMENTS }

/**
 * available play errors
 * @property NONE no error associated;
 * @property INVALID_SHOT invalid shot taken;
 * @property INVALID_TURN invalid turn;
 * @property GAME_OVER game over.
 */
enum class PlayError {
    NONE, INVALID_SHOT, INVALID_TURN, GAME_OVER // game over is not used
}

/**
 * Keeps the game and the correspondent consequence of a shot.
 * @property game BattleShip [Game];
 * @property consequence [ShotConsequence] after the shot was taken.
 */
data class PlayResult(val game: Game, val consequence: ShotConsequence)

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
    check(state == SETUP) { "Game Already Started" }
    val gameFight = copy(state = FIGHT)
    val player = st.start(gameName, boardA)
    return if (player == Player.B) {
        val gameFromDB = st.load(gameFight)
        val newGame = copy(boardA = gameFromDB.boardA, boardB = boardA, state = FIGHT, player = Player.B)
        newGame
    } else {
        gameFight
    }.also { st.store(it) } // MINDBLOWING
}

fun Game.refresh(st: Storage) : Game {
    return st.load(this)
}

/**
 *
 */
fun GameState.checkState(wantedState: GameState) {
    when (wantedState) {
        SETUP -> check(wantedState == this) { "Can't change fleet after game started" }
        FIGHT -> check(wantedState == this) { "Can't make a shot before start" }
        OVER -> check(wantedState == this) { "Game Over, FATALITY" }
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
    state.checkState(SETUP)

    val newBoard = boardA.putShip(type, pos, dir)
    return this.copy(boardA = newBoard)
}

/**
 *[Game] Function that will remove a ship if it's a valid command and ship
 * @param pos [Position] to remove the ship from
 * @return updated [Game]
 */
fun Game.removeShip(pos: Position): Game {
    state.checkState(SETUP)
    val newBoard = boardA.removeShip(pos)
    if (boardA === newBoard) error("No ship in $pos")
    return this.copy(boardA = newBoard)
}

/**
 *[Game] Function that will remove all ships if it's a valid command
 * @return updated [Game]
 */
fun Game.removeAll(): Game {
    state.checkState(SETUP)
    return this.copy(boardA = Board())
}

/**
 *[Game] Function that will add all ships if it's a valid command
 * @return updated [Game]
 */
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

/**
 *[Game] Function that will create the initial game
 * @return created game
 */
fun createGame(): Game {
    return Game("", Board(), Board())
}

/**
 *[Game] Function that will get a player board associated with a player, should not be used when boardB is null
 * @param target [Player] to find the board
 * @return Player [Board]
 * @exception IllegalStateException Cannot get other board, if boardb is nullable
 */
fun Game.getPlayerBoard(target: Player) : Board
{
    checkNotNull(boardB)
    return if (target == Player.A) boardA else boardB
}

/**
 * [Game] Function that will make a shot if it's a valid state and a valid shot
 * @param pos [Position] from the shot
 * @return [PlayResult] with the updated [Game] and [PlayError] associated
 */
fun Game.makeShot(pos: Position): PlayResult {
    state.checkState(FIGHT)
    require(player == turn) { "Wait for other player" }

    val enemyBoard = getPlayerBoard(player.other())
    checkNotNull(enemyBoard)
    val boardResult = enemyBoard.makeShot(pos)
    TODO("lol")
}
