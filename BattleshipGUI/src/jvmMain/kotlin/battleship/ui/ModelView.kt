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


class ModelView(val storage: Storage, val scope: CoroutineScope) {
    var game by mutableStateOf<Game>(createEmptyGame())
        private set
    var openDialogName by mutableStateOf(false)
        private set
    var message by mutableStateOf<String?>(null)
        private set
    var selectedType by mutableStateOf<ShipType?>(ShipType.values.first())
        private set
    var selectedDirection by mutableStateOf(Direction.HORIZONTAL)
        private set

    inline fun <reified T> getGame(): T =
        with(game) {
            check(this is T)
            return this
        }

    fun refresh() {
        val currGame = getGame<GameFight>()
        game = storage.load(currGame)
    }

    fun start(name: String? = null) {
        //TODO: start only in setup with fleet complete

        if (name == null) {
            openDialogName = true
        } else {
            //see if bracks
            val getSetUPGame = getGame<GameSetup>()
            game = getSetUPGame.startGame(name, storage)
            openDialogName = false
        }
    }

    fun putShip(pos: Position) {
        val currGame = getGame<GameSetup>()

        val type = selectedType ?: return
        val dir = selectedDirection

        val result = currGame.putShip(type, pos, dir);

        if (result.second === PutConsequence.NONE) {
            game = result.first;
        } else {
            //TODO: no profanity, carlos    YES SIR
            println("oh shit lol we got a 404 \"${result.second.name}\" press F1")
        }

        selectedType = ShipType.values.firstOrNull { shipType ->
            game.playerBoard.fleet.count { shipType === it.type } < shipType.fleetQuantity
        }
    }

    fun removeShip(pos: Position) {
        with(getGame<GameSetup>())
        {
            game = removeShip(pos);
        }
    }

    fun putAllRandom() {
        with(getGame<GameSetup>())
        {
            val res = this.putAllShips()
            if (res.second === PutConsequence.NONE)
                game = res.first
        }
    }

    fun removeAll() {
        with(getGame<GameSetup>())
        {
            game = removeAll()
        }
    }

    fun makeShot(pos: Position) {
        with(getGame<GameFight>()) {
            if (isNotYourTurn()) return
            val shotResult = makeShot(pos, storage);
            if (shotResult.second !== ShotConsequence.INVALID)
                game = shotResult.first
        }
    }

    fun setShipType(type: ShipType?) {
        selectedType = type
    }

    fun setDirection(direction: Direction) {
        selectedDirection = direction
    }

    fun closeDialog() {
        openDialogName = false
    }

    fun messageAck() {
        message = null
    }

}


