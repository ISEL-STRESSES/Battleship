// package battleship.storage
//
// import battleship.model.Player
// import battleship.model.board.Board
// import battleship.model.board.position.toPositionOrNull
// import mongoDB.*
//
// class MongoStorage(driver: MongoDriver) : Storage {
//
//    data class Doc(val _id: String, val movesA: List<String>, val movesB: List<String>)
//
//    private val collection = driver.getCollection<Doc>("games")
//
//    override fun start(name: String, board: Board): Player {
//        val doc = collection.getDocument(name)
//        if (doc != null) {
//            if (doc.movesA.isNotEmpty() && doc.movesB.isEmpty()) {
//                collection.replaceDocument(Doc(name, doc.movesA, board.map { it.toString()/*TODO*/ }))
//                return Player.B
//            } else {
//                collection.deleteDocument(name)
//            }
//        }
//        collection.insertDocument(Doc(name, board.map { it.toString()/*TODO*/ }, emptyList()))
//        return Player.A
//    }
//
//    override fun store(game: Game) {
//        collection.replaceDocument(
//            Doc(
//                game.name,
//                game.boardA.moves.map { it.toString()/*TODO*/ },
//                game.boardB.moves.map { it.toString()/*TODO*/ }
//            )
//        )
//    }
//
//    override fun load(game: Game): Game {
//        val doc = collection.getDocument(game.name)
//        checkNotNull(doc) { "No document in Load" }
//        if (doc.movesA.size == game.boardA.moves.size) {
//            return game
//        }
//        val pos = doc.movesA.last().toPositionOrNull() ?: error("Invalid Position in Load")
//        return game.copy(boardA = game.boardA.addMove(pos))
//    }
// }
