package battleship.ui

import battleship.model.*
import battleship.model.board.*
import battleship.model.ship.ShipType

const val BOARD_CHAR_DIM = COLUMN_DIM * 2 + 1
const val LETTERS_IDENT = 5
const val HORIZONTAL_IDENT = 3

const val horSep = '-'
const val verSep = '|'
const val cross = '+'

const val CHAR_MISS = 'O'
const val CHAR_SHIP = '#'
const val CHAR_HIT = '*'
const val CHAR_SUNK = 'X'
const val CHAR_WATER = ' '

//Horizontal separator condensed
val horizontalSeparators = CHAR_WATER.repeat(HORIZONTAL_IDENT) + cross + horSep.repeat(BOARD_CHAR_DIM) + cross

/**
 * Repeat the char [amount] times
 * @param amount amount of times to repeat the char
 */
fun Char.repeat(amount: Int) = "$this".repeat(amount)

/**
 * Returns the according char from the [Cell], blank space if it is none of the defined cells, meaning it is a "water" cell
 */
fun Cell.toChar(): Char {
    return when (this) {
        is ShipSunk -> CHAR_SUNK
        is ShipHit -> CHAR_HIT
        is ShipCell -> CHAR_SHIP
        is MissCell -> CHAR_MISS
        else -> CHAR_WATER
    }
}

/**
 * Function to print the HELP command
 */
fun printHelp() {
    println("Available Commands:")
    getCommandsOO().forEach {
        println("\t${it.key}\t\t${it.value.argsSyntax}")
    }
}

/**
 * Function to print the [Game]
 */
fun Game.print() {
    printColumnsIDX() // Prints column indexes
    println(horizontalSeparators) // Print top separator
    repeat(ROW_DIM) {
        boardA.printRow(it)
        printShipData(it, boardA)
        println()
    }
    println(horizontalSeparators) // Print bottom separator
}

/**
 * Prints the Column indexes of the board
 */
fun printColumnsIDX() {
    print(' '.repeat(LETTERS_IDENT))
    Column.values.forEach {
        print(it.letter.toString().padEnd(2))
    }
    println()
}

/**
 * Prints a single row of the board to the standard output
 * @param y index of the row to print
 */
fun Board.printRow(y: Int) {
    print(' '.repeat(HORIZONTAL_IDENT))
    print(verSep)
    repeat(COLUMN_DIM) { x ->
        val cell = grid[Position[x, y]]
        val cellChar = cell?.toChar() ?: CHAR_WATER
        print(" $cellChar")
    }
    print(" ") // print final space
    print(verSep)
}


/**
 * Prints the Ship information on the right of the [board]
 * @param idx index of the ship
 * @param board board to print the data with
 */
fun printShipData(idx: Int = -1, board: Board) {
    if (idx !in 0 until ShipType.values.size)
        return

    val type = ShipType.values[idx]
    val placedCount = board.fleet.count { it.type === type }

    print(" $placedCount x " + CHAR_SHIP.repeat(type.squares) + " of ${type.fleetQuantity} (${type.name})")
}
