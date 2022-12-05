fun main() {
    checkAndSolve("Day03", 157) { calculateSumOfCommonTypePriorities(it) }
    checkAndSolve("Day03", 70) { calculateSumOfCommonAcrossGroupsOfThree(it) }
}

private fun calculateSumOfCommonTypePriorities(lines: List<String>) =
    lines.sumOf { calculatePriority(findCommonType(it)) }

private fun uniqueCharCodesInString(content: String, from: Int = 0, to: Int = content.length): HashSet<Int> =
    (from until to).mapTo(HashSet()) { content[it].code }

private fun findCommonType(content: String): Char {
    val usedChars = uniqueCharCodesInString(content, 0, content.length / 2)
    for (i in content.length / 2 until content.length) {
        if (usedChars.contains(content[i].code))  {
            return content[i]
        }
    }
    throw IllegalStateException("Must have found common type")
}

private fun calculatePriority(charValue: Char): Int =
    when (charValue) {
        in 'a'..'z' -> charValue - 'a' + 1
        in 'A'..'Z' -> charValue - 'A' + 27
        else -> throw IllegalArgumentException()
    }

private fun calculateSumOfCommonAcrossGroupsOfThree(lines: List<String>): Int = (lines.indices step 3)
    .sumOf { calculatePriority(calculateCommonAcrossGroup(lines[it], lines[it +1], lines[it +2])) }

private fun calculateCommonAcrossGroup(first: String, second: String, third: String): Char {
    val charsFromFirst = uniqueCharCodesInString(first)
    val charsFromSecond = uniqueCharCodesInString(second)
    return third.find { charsFromFirst.contains(it.code) && charsFromSecond.contains(it.code) }
        ?: throw IllegalStateException("Common symbol for three strings not found")
}