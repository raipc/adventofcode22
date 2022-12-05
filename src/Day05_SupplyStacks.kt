fun main() {
    checkAndSolve("Day05", "CMZ") { solve(it.iterator(), MoveStrategy.ONE_BY_ONE) }
    checkAndSolve("Day05", "MCD") { solve(it.iterator(), MoveStrategy.ALL_AT_ONCE) }
}

private fun solve(lines: Iterator<String>, moveStrategy: MoveStrategy): String {
    val stacks = readInitialStacks(lines)
    for (line in lines) {
        moveStrategy.move(stacks, CommandParser.parse(line))
    }
    return buildString { stacks.forEach { if (it.isNotEmpty()) this.append(it.last().toChar()) } }
}

enum class MoveStrategy {
    ONE_BY_ONE {
        override fun move(stacks: Array<ArrayDeque<Int>>, command: Command) {
            for (i in 1..command.count) {
                stacks[command.to - 1].addLast(stacks[command.from - 1].removeLast())
            }
        }
    },
    ALL_AT_ONCE {
        override fun move(stacks: Array<ArrayDeque<Int>>, command: Command) {
            if (command.count == 1) {
                stacks[command.to - 1].addLast(stacks[command.from - 1].removeLast())
            } else {
                val removed = (1..command.count).map { stacks[command.from - 1].removeLast() }
                stacks[command.to - 1].addAll(removed.asReversed())
            }
        }
    };

    abstract fun move(stacks: Array<ArrayDeque<Int>>, command: Command)
}


private fun readInitialStacks(lines: Iterator<String>): Array<ArrayDeque<Int>> {
    var line = lines.next()
    val arrayCount = 9
    val result = (1..arrayCount).map { ArrayDeque<Int>() }.toTypedArray()
    do {
        if (!line[0].isDigit()) { // last line, skip
            if (line.length < 35) line = line.padEnd(length = 35, padChar = ' ')
            for (i in 0 until arrayCount) {
                line[i * 4 + 1].let { if (it != ' ') result[i].addFirst(it.code) }
            }
        }
        line = lines.next()
    } while (line.isNotEmpty())
    return result
}

object CommandParser {
    private const val pattern = "move X from Y to Z"
    private val indexOfCount = pattern.indexOf('X')
    private val indexOfFrom = pattern.indexOf('Y')
    private val indexOfTo = pattern.indexOf('Z')
    private val command = Command(0, 0, 0)

    fun parse(line: String): Command {
        val offset = line.length - pattern.length
        val crateCount = if (offset == 0) {
            line[indexOfCount].digitToInt()
        } else {
            line.substring(indexOfCount, indexOfCount + offset + 1).toInt()
        }
        val from = line[indexOfFrom + offset].digitToInt()
        val to = line[indexOfTo + offset].digitToInt()
        return command.apply {
            this.count = crateCount
            this.from = from
            this.to = to
        }
    }
}

data class Command(var count: Int, var from: Int, var to: Int)
