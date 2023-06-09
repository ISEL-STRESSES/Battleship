package battleship.ui

/**
 * Represents a command line with a name and arguments.
 * Example: ("PUT", ["Carrier", "h5", "Vertical"])
 */
typealias LineCommand = Pair<String, List<String>>

/**
 * Reads a command line after presenting the prompt.
 * Repeat until there is a line.
 * @throws RuntimeException thrown if standard input runs into a problem
 * @throws CharacterCodingException thrown if input is malformed
 */
fun readCommand(): LineCommand {
    while (true) {
        print("> ")
        return readln().parseCommand() ?: continue
    }
}

/**
 * Recognizes a line of text as a command.
 * The command name is capitalized.
 * @return The command line recognized or null if the line is empty.
 */
fun String.parseCommand(): LineCommand? {
    val words = trim().split(' ').filter { it.isNotBlank() }
    if (words.isEmpty()) return null
    val name = words[0].uppercase()
    val args = words.drop(1)
    return name to args
}
