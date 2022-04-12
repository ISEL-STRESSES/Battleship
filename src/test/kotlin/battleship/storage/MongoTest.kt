package battleship.storage

import mongoDB.MongoDriver
import mongoDB.getDocument
import mongoDB.insertDocument
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class MongoTest {
    data class Doc(val _id: String, val field: Int)

    @Test
    fun `test insert Document`() {
        val drv = MongoDriver()
        val collection = drv.getCollection<Doc>("test")
        collection.insertDocument(Doc("test_id", 10))
        val doc = collection.getDocument("test_id")
        assertNotNull(doc)
        assertEquals(10, doc.field)
    }
}
