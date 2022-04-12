package battleship.ui

import battleship.model.*
import battleship.model.Game.Player
import model.*
import battleship.model.Position.Column
import battleship.model.Position.Position

const val BOARD_CHAR_DIM = BOARD_DIM * 2 + 1
const val LETTERS_IDENT = 5
const val HORIZONTAL_IDENT = 3

fun repeatChar(size: Int, char: Char = ' ') = "$char".repeat(size)

val sep = '|'

val regLine = sep + " ".repeat(BOARD_CHAR_DIM) + sep

val horizontalBorders = repeatChar(HORIZONTAL_IDENT) + "+" + repeatChar(BOARD_CHAR_DIM, '-') + "+"

fun Player?.toChar() = this?.symbol ?: ' '

// Não testada + se quisermos escrever uma mensagem sem defenir um barco n dá
fun printLineOfBoard(id: String, fleetMsg: Boolean, ship: ShipType, amountOfShips: Int = 0) {
    if (fleetMsg)
        print(id.padStart(2).padEnd(3) + regLine + " $amountOfShips x ${"#".repeat(ship.squares)} of ${ship.fleetQuantity}\n")
    else
        print(id.padStart(2).padEnd(3) + regLine + "\n")
}

fun printBoard(board: Board) {
    print(repeatChar(LETTERS_IDENT))
    Column.values.forEach { print("${it.letter} ") }
    println()
    println(horizontalBorders)

    Position.values.forEachIndexed { _, pos ->
        if (pos.column.ordinal == BOARD_DIM - 1) {
            print(pos.row.number.toString().padStart(2).padEnd(3) + regLine)
            println()
        }
        print("${board.get(pos).toChar()}")
    }
    println(horizontalBorders)
    board.winner?.apply { println("Player $symbol wins.") }
}

fun main() {
    println("                                                                                           ".length)
    printBoard(Board())
}
