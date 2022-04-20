package battleship.ui

import battleship.model.Cell
import battleship.model.Game
import battleship.model.MissCell
import battleship.model.board.Board
import battleship.model.board.position.COLUMN_DIM
import battleship.model.board.position.Column
import battleship.model.board.position.ROW_DIM
import battleship.model.ship.ShipType

const val BOARD_CHAR_DIM = COLUMN_DIM * 2 + 1
const val LETTERS_IDENT = 5
const val HORIZONTAL_IDENT = 3

const val horSep = '-'
const val verSep = '|'
const val cross = '+'

fun Char.repeat(size: Int) = "$this".repeat(size)

val horizontalBorders = ' '.repeat(HORIZONTAL_IDENT) + "+" + '-'.repeat(BOARD_CHAR_DIM) + "+"

fun Cell.toChar(): Char {
    if (this is MissCell)
        return '*'

    return ' '
}

fun printShipData(idx: Int = -1, board: Board) {
    if (idx !in 0 until ShipType.values.size)
        return

    val type = ShipType.values[idx]
    val placedCount = board.fleet.count { it.type === type }

    print(" $placedCount x " + "#".repeat(type.squares) + " of ${type.fleetQuantity} (${type.name})")
}

/**
 * Prints a single row of the board to the standard output
 * @param rowIdx index of the row to print
 */
fun Board.printRow(rowIdx: Int) {
    print(' '.repeat(HORIZONTAL_IDENT))
    print(verSep)
    repeat(COLUMN_DIM) {
        val cellChar = " "
        print(" $cellChar")
    }
    print(" ") // print final char
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

private fun printCommand(cmd: String, args: String = "") {
    println("\t$cmd \t\t $args")
}

fun printHelp() {
    println("Available Commands:")
    printCommand("grid")
    printCommand("exit")
    println("\nSetup Phase:")
    printCommand("put", "(<shipType> [<position> <align>] | all)")
    printCommand("remove", "<position>")
    printCommand("start", "<gameId>")
    println("\nBattle Phase:")
    printCommand("shot", "<position>")
    printCommand("refresh")
}
