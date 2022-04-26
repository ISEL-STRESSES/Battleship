package battleship.storage

import battleship.model.Game
import battleship.model.Player
import battleship.model.board.Board
import battleship.model.board.Grid
import battleship.model.board.MissCell
import battleship.model.board.ShipHit
import battleship.model.ship.Ship
import mongoDB.MongoDriver
import mongoDB.deleteDocument
import mongoDB.getDocument
import mongoDB.insertDocument
import mongoDB.replaceDocument

/**
 *
 */
class MongoStorage(driver: MongoDriver) : Storage {

    /**
     * TODO()
     */
    data class Doc(val _id: String, val contentA: List<String>, val contentB: List<String>, val turn: String)

    /**
     * TODO
     */
    private val collection = driver.getCollection<Doc>("games")

    /**
     * TODO
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
     * TODO
     */
    private fun Ship.serialize(grid: Grid): String {
        val name = type
        val head = dir.name.first()
        val dir = dir
        val cellStatus = positions.map { if (grid[it] is ShipHit) '1' else '0' }.joinToString { "" }
        return "S $name $head $dir $cellStatus" // TODO: 26/04/2022 tha fuck is that "S"
    }

    /**
     * TODO
     */
    private fun Board.serialize(): FileContent {
        val fleetEntries = fleet.map { it.serialize(grid) }
        val missEntries = grid.entries.mapNotNull { if (it.value is MissCell) "M ${it.key}" else null }
        return fleetEntries + missEntries
    }

    /**
     * TODO
     */
    private fun String.deserialize() {
        val split = split(" ")
    }

    /**
     * TODO
     */
    private fun FileContent.deserialize(): Board {
        TODO()
        // return this.fold(Board()) { (acc, e) -> }
    }

    /**
     * TODO
     */
    override fun store(game: Game) {
        checkNotNull(game.boardB) // TODO("TAO FEIO CATANO")
        collection.replaceDocument(Doc(game.name, game.boardA.serialize(), game.boardB.serialize(), game.turn.other().name))
    }

    /**
     * TODO
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
