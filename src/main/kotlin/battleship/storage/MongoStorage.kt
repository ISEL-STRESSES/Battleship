package battleship.storage

import battleship.model.Game
import battleship.model.Player
import battleship.model.board.*
import battleship.model.ship.Ship
import battleship.model.ship.toShipType
import mongoDB.*


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
        val missEntries = grid.entries.mapNotNull { if (it.value is MissCell) "$MISS_INDICATOR ${it.key}" else null }
        return fleetEntries + missEntries
    }

    /**
     * Function that will deserialize a string from a file
     * @return pair with reference to the ship and the list of Cells from the ship
     */
    private fun String.deserialize(): Pair<Ship?, List<Cell>> {
        val split = split(" ")
        if (split.first() == SHIP_INDICATOR) {
            val type = split[1].toShipType()
            val head = split[2].toPosition()
            val dir = split[3].toDirection()
            val hits = split[4]
            val shipPositions = List(type.squares) { head.movePosition(dir, it) }
            val ship = Ship(type, head, dir, shipPositions)

            val shipCells = if(hits.all{ it == SHIP_CELL_SHOT})
                shipPositions.map { ShipSunk(it, ship) }
            else
                shipPositions.mapIndexed { num, it -> if(hits[num] == SHIP_CELL_SHOT) ShipHit(it, ship) else ShipCell(it, ship) }

            return ship to shipCells

        } else if (split.first() == MISS_INDICATOR) {
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
        val grid = entries.map { it.second }.flatten().associateBy { it.pos }

        return Board(ships, grid)
    }

    /**
     * TODO()
     */
    override fun start(name: String, board: Board): Player {
        val doc = collection.getDocument(name)
        if (doc != null) {
            if (doc.contentA.isNotEmpty() && doc.contentB.isEmpty()) {
                collection.replaceDocument(Doc(name, doc.contentA, board.serialize(), doc.turn))
                return Player.B
            } else {
                collection.deleteDocument(name)
            }
        }
        collection.insertDocument(Doc(name, board.serialize(), emptyList(), Player.A.name))
        return Player.A
    }

    /**
     * TODO()
     */
    override fun store(game: Game) {
        val boardAEntry = game.boardA.serialize()
        val boardBEntry = if (game.boardB != null) game.boardB.serialize() else emptyList()
        collection.replaceDocument(Doc(game.name, boardAEntry, boardBEntry, game.turn.name))
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
