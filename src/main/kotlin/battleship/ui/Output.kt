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

fun Char.repeat(size: Int) = "$this".repeat(size)

val horizontalBorders = ' '.repeat(HORIZONTAL_IDENT) + "+" + '-'.repeat(BOARD_CHAR_DIM) + "+"

fun printShipData(idx: Int = -1, board: Board) {
    if (idx !in 0 until ShipType.values.size)
        return

    val type = ShipType.values[idx]
    val placedCount = board.fleet.count { it.type === type }

    print(" $placedCount x " + "#".repeat(type.squares) + " of ${type.fleetQuantity} (${type.name})")
}

fun Cell.toChar(): Char {
    //ADOLF HITLER COM O NO FINAL, DE APELIDO MORGADO, QUEM SERÁ?
    return when (this) {
        is ShipSunk -> CHAR_SUNK
        is ShipHit -> CHAR_HIT
        is ShipCell -> CHAR_SHIP
        is MissCell -> CHAR_MISS
        else -> CHAR_WATER
    }
}

/**
 * Prints a single row of the board to the standard output
 * @param rowIdx index of the row to print
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

fun printColumnsIDX() {
    print(' '.repeat(LETTERS_IDENT))
    Column.values.forEach {
        print(it.letter.toString().padEnd(2))
    }
    println()
}

fun Game.print() {
    printColumnsIDX()
    println(horizontalBorders) // Print top separator
    repeat(ROW_DIM) {
        boardA.printRow(it)
        printShipData(it, boardA)
        println()
    }
    println(horizontalBorders) // Print bottom separator
}

/*
val regLine = sep + " ".repeat(BOARD_CHAR_DIM) + sep


fun Player?.toChar() = this?.id ?: ' '

// Não testada + se quisermos escrever uma mensagem sem defenir um barco n dá
fun printLineOfBoard(id: String, fleetMsg: Boolean, ship: ShipType, amountOfShips: Int = 0) {
    if (fleetMsg)
        print(
            id.padStart(2)
                .padEnd(3) + regLine + " $amountOfShips x ${"#".repeat(ship.squares)} of ${ship.fleetQuantity}\n"
        )
    else
        print(id.padStart(2).padEnd(3) + regLine + "\n")
}

fun printBoard(board: Board) {
    println()
    println(horizontalBorders)

    Position.values.forEachIndexed { _, pos ->
        if (pos.column.ordinal == BOARD_DIM - 1) {
            print(pos.row.number.toString().padStart(2).padEnd(3) + regLine)
            println()
        }
        print("${board.get(pos).toChar()}")
    }
    println(horizontalBorders)help

    board.winner?.apply { println("Player $symbol wins.") }
}
*/

fun printHelp() {
    println("Available Commands:")
    getCommandsOO().forEach {
        println("\t${it.key}\t\t${it.value.argsSyntax}")
    }
}
