package battleship.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import battleship.model.*
import battleship.model.board.Direction
import battleship.model.board.Position
import battleship.model.ship.ShipType
import battleship.storage.Storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val MESSAGE_TIMER = 4000L
const val AUTO_REFRESH_TIMER = 1000L


const val STATUS_WARN_INVALID_PUT_SHIP = "Ship type all used up"
const val STATUS_WARN_INVALID_PUT_POSITION = "Invalid Position"
const val STATUS_WARN_INVALID_SHOT = "Invalid Shot"
const val STATUS_WARN_INVALID_SHOT_WAIT_FOR_OTHER = "Wait for the enemy to start"
const val STATUS_WARN_INVALID_SHOT_TURN = "It's not your turn"


class ModelView(
    private val storage: Storage,
    private val scope: CoroutineScope
) {
    var game by mutableStateOf<Game>(createEmptyGame())
        private set
    var openDialogName by mutableStateOf(false)
        private set

    var warning by mutableStateOf<String?>(null)
    var message by mutableStateOf<String?>(null)
        private set
    private var messageJob by mutableStateOf<Job?>(null)
    var jobAutoRefresh by mutableStateOf<Job?>(null)
        private set
    var autoRefreshEnabled by mutableStateOf(true)
       private set
    var selectedType by mutableStateOf<ShipType?>(ShipType.values.first())
        private set
    var selectedDirection by mutableStateOf(Direction.HORIZONTAL)
        private set

    private fun sendMessage(msg: String) {
        messageJob?.cancel()
        message = msg
        messageJob = scope.launch { delay(MESSAGE_TIMER); message = null }
    }

    fun warningAck() {
        warning = null
    }

    inline fun <reified T> getGame(): T =
        with(game) {
            check(this is T)
            return this
        }

    fun refresh() {
        scope.launch { game = storage.load(getGame()) }
    }

    fun start(name: String? = null) {
        if (name == null) {
            openDialogName = true
        } else {

            if(!name.all { it.isWhitespace() || it.isLetterOrDigit()}) {
                warning = "Game name should only have alphanumeric characters and whitespaces"
                return
            }
            val currGame = getGame<GameSetup>()
            scope.launch {
                game = currGame.startGame(name, storage)
                openDialogName = false
                waitForOther()
            }
        }
    }
    fun setAutoRefresh(auto : Boolean) {
        autoRefreshEnabled = auto
        if (autoRefreshEnabled) {
            waitForOther()
        }
        else {
            cancelAutoRefresh()
        }
    }

    private fun shouldAutoRefresh(g : GameFight) =
        (g.isNotYourTurn || (g.isYourTurn && g.enemyBoard.fleet.isEmpty())) && g.winner == null

    /**
     * If necessary, launch a coroutine to do periodic readings until it's the turn to play.
     * To call at the start of the game and after each move.
     */
    private fun waitForOther() {
        var g = getGame<GameFight>()
        if (autoRefreshEnabled && !shouldAutoRefresh(g)) return
        jobAutoRefresh = scope.launch {
            do {
                delay(AUTO_REFRESH_TIMER)
                g = storage.load(g)
                game = g
            } while (shouldAutoRefresh(g))
            jobAutoRefresh = null
        }
    }


    private fun cancelAutoRefresh() {
        jobAutoRefresh?.cancel()
        jobAutoRefresh = null
        autoRefreshEnabled = false
    }

    fun putShip(pos: Position) {
        val currGame = getGame<GameSetup>()

        val type = selectedType ?: return
        val dir = selectedDirection

        val result = currGame.putShip(type, pos, dir)

        if (result.second === PutConsequence.NONE) {
            game = result.first
        } else {
            when (result.second) {
                PutConsequence.INVALID_SHIP -> sendMessage(STATUS_WARN_INVALID_PUT_SHIP)
                PutConsequence.INVALID_POSITION -> sendMessage(STATUS_WARN_INVALID_PUT_POSITION)
                else -> {}
            }
        }

        selectAvailableType()
    }

    fun removeShip(pos: Position) {
        with(getGame<GameSetup>())
        {
            game = removeShip(pos)
            selectAvailableType()
        }
    }

    fun putAllRandom() {
        with(getGame<GameSetup>())
        {
            val res = this.putAllShips()
            if (res.second === PutConsequence.NONE) {
                game = res.first
                selectAvailableType()
            }
        }
    }

    fun removeAll() {
        with(getGame<GameSetup>())
        {
            game = removeAll()
            selectedType = ShipType.values.first()
        }
    }

    fun makeShot(pos: Position) {
        with(getGame<GameFight>()) {
            if(winner != null) return

            if (this.enemyBoard.fleet.isEmpty()) {
                sendMessage(STATUS_WARN_INVALID_SHOT_WAIT_FOR_OTHER)
                return
            }
            val shotResult = makeShot(pos, storage, scope)
            if (shotResult.second !== ShotConsequence.INVALID && shotResult.second !== ShotConsequence.NOT_YOUR_TURN) {
                game = shotResult.first
                waitForOther()
            }
            else {
                when (shotResult.second) {
                    ShotConsequence.NOT_YOUR_TURN -> sendMessage(STATUS_WARN_INVALID_SHOT_TURN)
                    ShotConsequence.INVALID -> sendMessage(STATUS_WARN_INVALID_SHOT)
                    else -> {}
                }
            }
        }
    }

    fun setShipType(type: ShipType) {
        selectedType = type.takeIf {
            shipType -> game.playerBoard.fleet.count { shipType === it.type } < type.fleetQuantity
        }
    }

    private fun selectAvailableType() {
        selectedType = ShipType.values.firstOrNull { shipType ->
            game.playerBoard.fleet.count { shipType === it.type } < shipType.fleetQuantity
        }
    }

    fun setDirection(direction: Direction) {
        selectedDirection = direction
    }

    fun closeDialog() {
        openDialogName = false
    }
}
