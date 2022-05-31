package battleship.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import battleship.model.*
import battleship.model.board.Position
import battleship.model.board.Direction
import battleship.model.ship.ShipType
import battleship.storage.Storage


class ModelView(val storage: Storage) {
    var game by mutableStateOf<Game>(createGame())
        private set
    var openDialogName by mutableStateOf(false)
        private set
    var message by mutableStateOf<String?>(null)
        private set
    var selectedType by mutableStateOf<ShipType?>(null)
        private set
    var selectedDirection by mutableStateOf(Direction.VERTICAL) // TODO: set back to null
        private set




    fun refresh() {
        game = storage.load(game);
    }
    fun start(){
        TODO()
    }

    fun putShip(pos : Position)
    {
        val type = selectedType ?: return
        val dir = selectedDirection

        val result = game.putShip(type, pos, dir);

        if(result.second === PutConsequence.NONE)
        {
            game = result.first;
        } else {
            println("oh shit lol we got a 404 \"${result.second.name}\" press F1")
        }
    }

    fun removeShip(pos: Position){
        game = game.removeShip(pos)

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

        //TODO: resolve new ship type if no more types are available
    }
    fun setDirection(direction: Direction)
    {
        selectedDirection = direction
    }



}

fun GameState.hasNotStarted() = this == GameState.SETUP

fun GameState.hasStarted() =  this != GameState.SETUP

