package battleship.storage

import battleship.model.Game
import battleship.model.Player
import battleship.model.board.*
import battleship.model.ship.Ship
import battleship.model.ship.toShipType
import battleship.model.ship.toShipTypeOrNull
import mongoDB.MongoDriver
import mongoDB.deleteDocument
import mongoDB.getDocument
import mongoDB.insertDocument
import mongoDB.replaceDocument


const val SHIP_INDICATOR = "S"
const val MISS_INDICATOR = "M"
const val SHIP_CELL_SHOT = '1'
const val SHIP_CELL = '0'


class MongoStorage(driver: MongoDriver) : Storage {

    /**
     * Representation of the game state in a document.
     */
    data class Doc(val _id: String, val contentA: List<String>, val contentB: List<String>, val turn: String)

    /**
     * The collection with all the games.
     */
    private val collection = driver.getCollection<Doc>("games")

    /**
     * Function that will serialize a [Ship] into a string
     * @param grid [Grid] grid where the ship is located
     * @return string information in string
     */
    private fun Ship.serialize(grid: Grid): String {
        val name = type.name
        val cellStatus = positions.map { if (grid[it] is ShipHit) SHIP_CELL_SHOT else SHIP_CELL }.joinToString("")
        return ("$SHIP_INDICATOR $name $head $dir $cellStatus")
    }

    /**
     * Function that will serialize a [Board] into a [FileContent]
     * @return [FileContent] with all the content that will be uploaded into the [Storage]
     */
    private fun Board.serialize(): FileContent {
        val fleetEntries = fleet.map { it.serialize(grid) }
        val missEntries = grid.entries.mapNotNull { if (it.value is MissCell) "M ${it.key}" else null }
        return fleetEntries + missEntries
    }

    /**
     * TODO()
     */
    private fun String.deserialize() :Pair<Ship?, List<Cell>> {
        val split = split(" ")
        if (split.first() == SHIP_INDICATOR){
            val pos = split.drop(4).map { it.toPosition() }
            val type = split[1].toShipType()
            val head = split[2].toPosition()
            val dir = split[3].toDirection()
            val shipCells = List(type.squares) { head.movePosition(dir, it) }
            val ship = Ship(type, head, dir, shipCells)
            return ship to split.last().mapIndexed { idx, elem ->
                if( elem == SHIP_CELL_SHOT) ShipHit(pos[idx], ship)
                else ShipCell(pos[idx], ship)
            }

        } else if(split.first() == MISS_INDICATOR){
            val pos = split[1].toPosition()
            val newCell = MissCell(pos)
            return null to listOf(newCell)
        }
        return null to emptyList()
    }

    /**
     * TODO()
     */
    private fun FileContent.deserialize(): Board {
        val entries = map { it.deserialize() }
        val ships = entries.mapNotNull { it.first }
        val grid = entries.map{ it.second }.flatten().associateBy { it.pos }

        return Board(ships, grid)
    }

    /**
     * TODO()
     */
    override fun store(game: Game) {
        checkNotNull(game.boardB) // TODO("TAO FEIO CATANO")
        collection.replaceDocument(Doc(game.name, game.boardA.serialize(), game.boardB.serialize(), game.turn.other().name))
    }

    /**
     * TODO()
     */
    override fun load(game: Game): Game {
        val doc = collection.getDocument(game.name)
        checkNotNull(doc) { "No document in Load" }
        checkNotNull(game.boardB) { TODO("FAZER MELHOR") }
        if (game.turn == Player.A && game.turn.name == doc.turn) {
            // game is updated
            if (doc.contentB.size == game.boardB.grid.size) {
                return game
            }
            return game.copy(boardB = doc.contentB.deserialize())
        } else if (game.turn == Player.B && game.turn.name == doc.turn) {
            // game is updated
            if (doc.contentA.size == game.boardA.grid.size) {
                return game
            }
            return game.copy(boardA = doc.contentA.deserialize())
        }
        return game
    }
}
