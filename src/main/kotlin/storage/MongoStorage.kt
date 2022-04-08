package storage

import model.Game
import model.Player
import mogoDB.*

class MongoStorage(driver: MongoDriver) : Storage {
    data class Doc(val _id: String, val moves: List<Int>)

    private val collection = driver.getCollection<Doc>("games")

    override fun start(name: String): Player {
        val doc = collection.getDocument(name)
        if (doc != null) {
            if (doc.moves.size <= 1) {
                return Player.B
            } else {
                collection.deleteDocument(name)
            }
        }
        collection.insertDocument(Doc(name, emptyList()))
        return Player.A
    }

    override fun store(game: Game) {
        collection.replaceDocument(Doc(game.name, game.board.moves.map { it.pos.index }))
    }

    override fun load(game: Game): Game {
        val doc = collection.getDocument(game.name)
        checkNotNull(doc) { "No document in Load" }
        if (doc.moves.size == game.board.moves.size) {
            return game
        }
        val pos = doc.moves.last().toPositionOrNull() ?: error("Invalid Position in Load")
        return game.copy(board = game.board.addMove(pos))
    }
}
