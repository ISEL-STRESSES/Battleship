package ui

import model.BOARD_DIM
import model.Board
import model.Player
import model.get
import model.position.Column
import model.position.Position

const val BOARD_CHAR_DIM = BOARD_DIM * 2 + 1
const val LETTERS_IDENT = 5
const val HORIZONTAL_IDENT = 3

fun repeatChar(size: Int, char: Char = ' ') = "$char".repeat(size)

val sep = '|'

val regLine = sep + " ".repeat(BOARD_CHAR_DIM) + sep

val horizontalBorders = repeatChar(HORIZONTAL_IDENT) + "+" + repeatChar(BOARD_CHAR_DIM, '-') + "+"

fun Player?.toChar() = this?.symbol ?: ' '

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
