package battleship.storage

import mongoDB.MongoDriver
import mongoDB.getDocument
import mongoDB.insertDocument
import mongoDB.replaceDocument
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class MongoTest {
    data class Doc(val _id: String, val field: Int)

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
}
