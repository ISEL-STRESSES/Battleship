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
 * [Game] Function that will start the game if it meets the requirements
 * @param gameName name of the game
 * @param st storage to use
 * @return updated [Game]
 */
fun Game.startGame(gameName: String, st: Storage): Game {
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
typealias GamePut = Pair<Game, PutConsequence>


/**
 * [Game] Function that will put a ship if it's a valid command and ship
 * @param type [ShipType] of the ship to put
 * @param pos head [Position] of the ship
 * @param dir [Direction] of the ship
 * @return updated [GamePut]
 */
fun Game.putShip(type: ShipType, pos: Position, dir: Direction): GamePut {

    val result = boardA.putShip(type, pos, dir)
    return GamePut(this.copy(boardA = result.first), result.second)
}

/**
 * Function that puts a random ship in the [Board].
 * @receiver Game to alter.
 * @param type Type of ship.
 * @return Returns a game and it's consequence.
 */
fun Game.putRandomShip(type: ShipType): GamePut {
    val result = boardA.putRandomShip(type)
    return GamePut(this.copy(boardA = result.first), result.second)
}


/**
 * [Game] Function that will add all ships if it's a valid command
 * @return updated [Game]
 */
fun Game.putAllShips(): GamePut {
    val result = boardA.putAllShips()
    return GamePut(this.copy(boardA = result.first), result.second)
}


/**
 *[Game] Function that will remove a ship if it's a valid command and ship
 * @param pos [Position] to remove the ship from
 * @return updated [Game]
 */
fun Game.removeShip(pos: Position): Game {
    val newBoard = boardA.removeShip(pos)
    return this.copy(boardA = newBoard)
}

/**
 *[Game] Function that will remove all ships if it's a valid command
 * @return updated [Game]
 */
fun Game.removeAll(): Game {
    return this.copy(boardA = Board())
}


/**
 *[Game] Function that will create the initial game
 * @return created game
 */
fun createGame() = Game("", Board(), Board())

/**
 *[Game] Function that will get a player board associated with a player, should not be used when boardB is null
 * @param target [Player] to find the board
 * @return Player [Board]
 * @exception IllegalStateException Cannot get other board, if boardb is nullable
 */
fun Game.getPlayerBoard(target: Player): Board? {
    return if (target == Player.A) boardA else boardB
}


/**
 * [Game] Function that will make a shot if it's a valid state and a valid shot
 * @param pos [Position] from the shot
 * @return [GameShot] with the updated [Game] and [ShotConsequence] associated
 */
fun Game.makeShot(pos: Position, st: Storage): GameShot {

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
