package battleship.storage

import battleship.model.Game
import battleship.model.board.Board
import mongoDB.MongoDriver
import mongoDB.getDocument
import mongoDB.insertDocument
import mongoDB.replaceDocument
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class MongoTest {
    data class Doc(val _id: String, val field: Int)

    data class GameDoc(val _id: String, val boardA :Board, val boardB: Board)

    private fun Game.toGameDoc() = boardB?.let { GameDoc(name, boardA, it) }

    private val docName = "testing"

    @Test
    fun `test insert Document`() = MongoDriver("PVV").use {
        val collection = it.getCollection<Doc>(docName)
        val doc = collection.getDocument("${docName}_id")
        if (doc == null)
            collection.insertDocument(Doc("${docName}_id", 10))
        else collection.replaceDocument(Doc("${docName}_id", 10))
        val updated = collection.getDocument("${docName}_id")
        assertNotNull(updated)
        assertEquals(10, updated.field)
    }
    @Test
    fun `testing unchanged game`() = MongoDriver("Game_test").use{
        val gameDoc = Game("test1", Board(), Board())

        val collection = it.getCollection<GameDoc>(docName)
        val doc = collection.getDocument("${docName}_id")
        if (doc == null)
            collection.insertDocument(GameDoc("${docName}_id",gameDoc.boardA, gameDoc.boardB!!))
        else collection.replaceDocument(GameDoc("${docName}_id",gameDoc.boardA, gameDoc.boardB!!))
        val updated = collection.getDocument("${docName}_id")
        assertNotNull(updated)
        assertEquals(gameDoc.toGameDoc(), updated)
    }
}
