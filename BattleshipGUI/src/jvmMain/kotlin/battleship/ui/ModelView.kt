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


    private fun waitForOther() {
        if (game.state === GameState.SETUP) return
        if (game.player === game.turn) return

        //TODO: Do rest later lol
        return
    }


    fun refresh() {
        game = storage.load(game);
    }

    fun start(name: String? = null) {
        //TODO: start only in setup with fleet complete
        if (name == null) {
            openDialogName = true
        } else {
            game = game.startGame(name, storage)
            openDialogName = false
        }
    }

    fun putShip(pos: Position) {
        val type = selectedType ?: return
        val dir = selectedDirection

        val result = game.putShip(type, pos, dir);

        if (result.second === PutConsequence.NONE) {
            game = result.first;
        } else {
            println("oh shit lol we got a 404 \"${result.second.name}\" press F1")
        }

        selectedType = ShipType.values.firstOrNull { shipType ->
            game.boardA.fleet.count { shipType === it.type } < shipType.fleetQuantity
        }
    }

    fun removeShip(pos: Position) {
        //TODO: make sure only able on setup, put on game this
        game = game.removeShip(pos)

    }

    fun putAllRandom() {
        val res = game.putAllShips()
        if (res.second === PutConsequence.NONE)
            game = res.first
    }

    fun removeAll() {
        game = game.removeAll()
    }

    fun makeShot(pos : Position) {
        if(game.isNotYourTurn()) return
        val shotResult = game.makeShot(pos, storage);
        //val typeHit = shotResult.third;
        if(shotResult.second !== ShotConsequence.INVALID)
            game = shotResult.first
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


