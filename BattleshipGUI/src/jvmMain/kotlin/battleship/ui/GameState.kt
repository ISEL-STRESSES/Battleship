package battleship.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import battleship.model.*
import battleship.model.board.Direction
import battleship.model.ship.ShipType
import battleship.storage.Storage


class GameState(val storage: Storage) {
    var game by mutableStateOf<Game>(createGame())
        private set
    var openDialogName by mutableStateOf(false)
        private set
    var message by mutableStateOf<String?>(null)
        private set
    var selectedType by mutableStateOf<ShipType?>(null)
        private set
    var selectedDirection by mutableStateOf<Direction?>(Direction.HORIZONTAL) // TODO: set back to null
        private set




    fun refresh() {
        game = storage.load(game);
    }
    fun start(){
        TODO()
    }

    fun putAllRandom() {
        val res = game.putAllShips()
        if(res.second === PutConsequence.NONE)
            game = res.first
    }

    fun removeAll() {
        game = game.removeAll()
    }

    fun setShipType(type : ShipType?) {
        selectedType = type
    }

    fun setDirection(direction: Direction?)
    {
        selectedDirection = direction
    }

}

fun GameState.hasNotStarted() = game.state == battleship.model.GameState.SETUP

fun GameState.hasStarted() =  game.state != battleship.model.GameState.SETUP

