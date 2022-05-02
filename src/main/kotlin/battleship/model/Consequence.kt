package battleship.model

import battleship.model.ShotConsequence.*
import battleship.model.board.Board


typealias PutResult = Pair<Board, PutConsequence>
typealias ShotResult = Pair<Board, ShotConsequence>

/**
 * Class that represents the consequence of the shot taken
 * @property MISS shot was missed
 * @property HIT shot was hit
 * @property SUNK shot has sunken a ship
 * @property INVALID shot was invalid
 */
enum class ShotConsequence {
    MISS, HIT, SUNK, INVALID
}

/**
 * Class that as all the possible outcomes of the put Command.
 */
enum class PutConsequence { NONE, INVALID_SHIP, INVALID_POSITION, INVALID_RANDOM }