package battleship.model

import battleship.model.GameState.FIGHT
import battleship.model.GameState.SETUP
import battleship.model.PlayError.*
import battleship.model.board.*
import battleship.model.ship.ShipType
import battleship.storage.Storage

/**
 * available play errors
 * @property NONE no error associated;
 * @property INVALID_SHOT invalid shot taken;
 * @property INVALID_TURN invalid turn;
 * @property GAME_OVER game over.
 */
enum class PlayError {
    NONE, INVALID_SHOT, INVALID_TURN, GAME_OVER
}

/**
 * Keep the current state of the game.
 * @property SETUP setup stage where only PUT commands will be allowed;
 * @property FIGHT fight stage where you can do all the commands except the ones in the [SETUP] phase;
 */
enum class GameState {
    SETUP, FIGHT
}

/**
 * Central object that represents the battleship game
 * @property boardA [Board] of the first player
 */
sealed class Game(val playerBoard: Board)

class GameSetup(playerBoard: Board) : Game(playerBoard)

class GameFight(
    playerBoard : Board,
    val enemyBoard : Board,
    val name : String,
    val player : Player,
    val turn : Player
) : Game(playerBoard)

/**
 * [Game] Function that will start the game if it meets the requirements
 * @param gameName name of the game
 * @param st storage to use
 * @return updated [Game]
 */
fun GameSetup.startGame(gameName: String, st: Storage): Game {
    val gameFight = copy(name = gameName, state = FIGHT)
    val player = st.start(gameName, boardA)
    return if (player == Player.B) {
        val gameFromDB = st.load(gameFight)
        val newGame = gameFight.copy(boardA = gameFromDB.boardA, boardB = boardA, state = FIGHT, player = Player.B)
        newGame.also { st.store(it) }
    } else {
        gameFight
    }
}

/**
 * Keeps the game and the correspondent consequence of a put.
 * @property [Game] battleship game;
 * @property [PutConsequence] after the put was done.
 */
typealias GamePut = Pair<GameSetup, PutConsequence>


/**
 * [Game] Function that will put a ship if it's a valid command and ship
 * @param type [ShipType] of the ship to put
 * @param pos head [Position] of the ship
 * @param dir [Direction] of the ship
 * @return updated [GamePut]
 */
fun GameSetup.putShip(type: ShipType, pos: Position, dir: Direction): GamePut {

    val result = playerBoard.putShip(type, pos, dir)
    return GamePut(GameSetup(result.first), result.second)
}

/**
 * Function that puts a random ship in the [Board].
 * @receiver Game to alter.
 * @param type Type of ship.
 * @return Returns a game and it's consequence.
 */
fun GameSetup.putRandomShip(type: ShipType): GamePut {
    val result = playerBoard.putRandomShip(type)
    return GamePut(GameSetup(result.first), result.second)
}


/**
 * [GameSetup] Function that will add all ships if it's a valid command
 * @return updated [Game]
 */
fun GameSetup.putAllShips(): GamePut {
    val result = playerBoard.putAllShips()
    return GamePut(GameSetup(result.first), result.second)
}


/**
 * [GameSetup] Function that will remove a ship if it's a valid command and ship
 * @param pos [Position] to remove the ship from
 * @return updated [Game]
 */
fun GameSetup.removeShip(pos: Position): GameSetup {
    return GameSetup(playerBoard.removeShip(pos))
}

/**
 *[Game] Function that will empty the player board
 * @return updated [Game]
 */
fun GameSetup.removeAll(): GameSetup {
    return createEmptyGame()
}


/**
 *[Game] Function that will create the initial game
 * @return created game
 */
fun createEmptyGame() = GameSetup(Board())

/**
 * [Game] Function that will make a shot if it's a valid state and a valid shot
 * @param pos [Position] from the shot
 * @return [GameShot] with the updated [Game] and [ShotConsequence] associated
 */
fun GameFight.makeShot(pos: Position, st: Storage): GameShot {

    check(isYourTurn())

    val playerBoard = getPlayerBoard(player)
    val enemyBoard = getPlayerBoard(player.other())
    checkNotNull(enemyBoard)
    val boardResult = enemyBoard.makeShot(pos)

    val newBoardA = if (playerBoard == boardA) boardA else boardResult.first
    val newBoardB = if (playerBoard == boardB) boardB else boardResult.first

    if (boardResult.second === ShotConsequence.INVALID)
        return GameShot(this, ShotConsequence.INVALID, null)

    val newTurn = if (boardResult.second === ShotConsequence.MISS)
            turn.other()
        else
            turn

    val newGame = copy(boardA = newBoardA, boardB = newBoardB, turn = newTurn)

    st.store(newGame)

    return GameShot(newGame, boardResult.second, boardResult.third)
}

fun GameFight.isYourTurn() = turn !== player
fun GameFight.isNotYourTurn() = !isYourTurn()

fun Game.hasStarted() = this !is GameSetup
fun Game.hasNotStarted() = !hasStarted()

