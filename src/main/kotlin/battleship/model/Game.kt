package battleship.model

import battleship.model.GameState.*
import battleship.model.PlayError.*
import battleship.model.board.*
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

typealias LigmaG = Pair<Position, Direction>


fun Board.getPossiblePositions(size : Int, dir: Direction) : List<LigmaG>
{
    val mList = mutableListOf<LigmaG>()

    for(x in 0 until COLUMN_DIM - size - 1)
    {
        for(y in 0 until ROW_DIM - size - 1)
        {
            val origin = Position[x,y]
            val positions = List(size) { origin.movePosition(dir, it) }
            if(positions.all{grid[it] == null}) mList.add(origin to dir)
        }
    }

    return mList
}


fun Game.putRandomShip(type : ShipType) : Game
{
    val allowSpace = boardA.getPossiblePositions(type.squares, Direction.HORIZONTAL) +
                     boardA.getPossiblePositions(type.squares, Direction.VERTICAL)

    if(allowSpace.isEmpty())
    {
        return this
    }
    val randomState = allowSpace.random()
    val newBoard = boardA.putShip(type, randomState.first, randomState.second)
    return this.copy(boardA = newBoard)
}

fun Game.putAllShips() : Game
{
    var newGame = this
    do {
        val possiblePool = ShipType.values.filter{ type -> type.fleetQuantity - boardA.fleet.count{ship -> ship.type == type} > 0 }
        newGame = newGame.putRandomShip(possiblePool.random())
    } while(newGame != this)
    return newGame
}

/*
/**
 *[Game] Function that will add all ships if it's a valid command
 * @return updated [Game]
 */
fun Game.putAllShips() {
    state.checkPutState()
    while(true){
        val pos = Position.values.random()
        val currShip = ShipType.values.forEach {
            super.hiputS
        }/
//        this.putShip(currShip, pos)
//    }
    TODO()
}

 */


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
    state.checkState(SETUP)
    return this.copy(boardA = Board())
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
fun Game.getPlayerBoard(target: Player): Board? {
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

    val newBoardA = if(playerBoard == boardA) boardA else boardResult.board
    val newBoardB = if(playerBoard == boardB) boardB else boardResult.board

    val newTurn = if (boardResult.consequence === ShotConsequence.HIT || boardResult.consequence === ShotConsequence.SUNK)
    {
        turn
    } else {
        turn.other()
    }

    println(boardResult.consequence)
    println(ShipType.values.sumOf {it.squares})

    val newGame = copy(boardA = newBoardA, boardB = newBoardB, turn = newTurn)

    if(boardResult.consequence !== ShotConsequence.INVALID)
    {
        st.store(newGame)
    }

    return PlayResult(newGame, boardResult.consequence)
}
